import '../App.css'
import React from 'react'
import Cookies from 'js-cookie'
import Cookies_Pevneva from '../settings/Cookies_Pevneva'
import RequestUrls_Pevneva from '../settings/RequestUrls_Pevneva'
import QuestionTypes_Pevneva from '../settings/QuestionTypes_Pevneva'
import { Box, Button, ListItem, Stack, Typography } from '@mui/material'
import CircleGraphic_Pevneva from '../components/graphics/CircleGraphic_Pevneva'
import BarGraphic_Pevneva from '../components/graphics/BarGraphic_Pevneva'
import List from '@mui/material/List'
import ListItemButton from '@mui/material/ListItemButton'
import ListItemIcon from '@mui/material/ListItemIcon'
import ListItemText from '@mui/material/ListItemText'
import Collapse from '@mui/material/Collapse'
import ExpandLess from '@mui/icons-material/ExpandLess'
import ExpandMore from '@mui/icons-material/ExpandMore'
import ThumbUpOffAltIcon from '@mui/icons-material/ThumbUpOffAlt'
import ThumbDownOffAltIcon from '@mui/icons-material/ThumbDownOffAlt'
import QuestionAnswerIcon from '@mui/icons-material/QuestionAnswer'

export default function StatisticsSurveyPage_Pevneva(props) {
  const[questionsList, setQuestionsList] = React.useState([]) 
  const[firstLoad, setFirstLoad] = React.useState(true)

  if (firstLoad) {
    setFirstLoad(false)
    loadQuestions()
  }

  async function loadQuestions() {
    const request_body = {
      'login': Cookies.get(Cookies_Pevneva.LOGIN),
      'session_id': Cookies.get(Cookies_Pevneva.SESSION_ID)
    }

    let questions = await fetch(RequestUrls_Pevneva.SURVEY + '/' + props.params.id + RequestUrls_Pevneva.GET_QUESTIONS_WITH_ANSWERS, {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify(request_body)
    })
    questions = await questions.json()

    setQuestionsList(questions)
  }

  function formattedAnswer(answer, type){
    return (type == QuestionTypes_Pevneva.BOOLEAN ? (answer === 'true' ? 'Да' : 'Нет') : answer)
  }

  function ListOfAnswers(props) {
    const [open, setOpen] = React.useState(false)

    const handleClick = () => {
      setOpen(!open)
    }

    return (
      <div style={{width: 'auto'}}>
        <ListItemButton onClick={handleClick}>
          <ListItemIcon>
            <QuestionAnswerIcon />
          </ListItemIcon>
          <ListItemText primary='Все ответы' />
          {open ? <ExpandLess /> : <ExpandMore />}
        </ListItemButton>
        <Collapse in={open} timeout='auto' unmountOnExit>
          <List component='div' disablePadding>
            {props.answers.map(answer => {
              return (
                <ListItem sx={{ pl: 6 }}>
                  <ListItemText primary={answer.owner + ': ' + formattedAnswer(answer.answer, props.questionType)} />
                  <ListItemIcon style={{marginLeft: '10px'}}>
                    {(props.questionAnswer === answer.answer ? <ThumbUpOffAltIcon /> : <ThumbDownOffAltIcon />)}
                  </ListItemIcon>
                </ListItem>
              )
            })}
          </List>
        </Collapse>
      </div>
    )
  }

  return (
    <div id='ViewSurveyPage'>
      <div id='questionsList'>
        {questionsList.map(function(question) {  
          let data = []
          let dataKeys = []

          question.answers.forEach(answer => {
            if (data[answer.answer] === undefined) {
              data[answer.answer] = {name: formattedAnswer(answer.answer, question.type), value: 0}
              dataKeys.push(answer.answer)
            }
            data[answer.answer].value++
          })
          dataKeys.sort()

          let formattedData = []
          dataKeys.forEach(key => {
            formattedData.push(data[key])
          })
          
          return(
            <div className='questionStatistics'>
              <div className='questionTitle'>{question.wording}</div>
              <Stack direction='row' alignItems='center' gap={1}>
                <ThumbUpOffAltIcon />
                <Typography variant='body1'>{formattedAnswer(question.answer, question.type)}</Typography>
              </Stack>
              <CircleGraphic_Pevneva data={formattedData}/>
              <BarGraphic_Pevneva data={formattedData}/>
              <ListOfAnswers answers={question.answers} questionType={question.type} questionAnswer={question.answer} />
            </div>
          )
        })}
        
        <Box sx={{ paddingTop: 20 }} />
      </div>
    </div>
  )
}