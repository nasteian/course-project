import '../App.css'
import React from 'react'
import Cookies from 'js-cookie'
import Cookies_Pevneva from '../settings/Cookies_Pevneva'
import RequestUrls_Pevneva from '../settings/RequestUrls_Pevneva'
import Strings_Pevneva from '../settings/Strings_Pevneva'
import Question_Pevneva from '../components/Question_Pevneva'
import { Box, Button } from '@mui/material'
import toast from 'react-hot-toast'

export default class ViewSurveyPage_Pevneva extends React.Component {
  constructor(props) {
    super(props)

    this.state = {
      questionsList: [],
      answers: []
    }
    
    this.onSaveAnswerClick = this.onSaveAnswerClick.bind(this)
    this.saveAnswer = this.saveAnswer.bind(this)
    this.answerChanged = this.answerChanged.bind(this)
    
    this.loadQuestions()
  }

  loadQuestions = async () => {
    let questions = await fetch(RequestUrls_Pevneva.SURVEY + '/' + this.props.params.id + RequestUrls_Pevneva.GET_ALL_QUESTIONS, {
      method: 'GET',
      headers: {'Content-Type': 'application/json'}
    })
    questions = await questions.json()

    this.setState({questionsList: questions})
  }

  onSaveAnswerClick = () => {
    toast.promise(
      this.saveAnswer(), {
         loading: Strings_Pevneva.SAVING,
         success: <b>{Strings_Pevneva.ANSWERS_SAVED}</b>,
         error: <b>{Strings_Pevneva.OCCURED_ERROR}</b>
       }
    )
  }

  saveAnswer = () => {
    return new Promise((resolve, reject) => {
      let answers = []
      this.state.questionsList.forEach(question => {
        answers.push({id: question['id'], answer: this.state.answers[question['id']]})
      })
      
      const request_body = {
        'login': Cookies.get(Cookies_Pevneva.LOGIN),
        'session_id': Cookies.get(Cookies_Pevneva.SESSION_ID),
        'answers': answers
      }
    
      fetch(RequestUrls_Pevneva.SURVEY + '/' + this.props.params.id + RequestUrls_Pevneva.UPDATE_ANSWERS, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(request_body)
      }).then(response => {
        if (response.status === 200) resolve()
        if (response.status === 423) toast.error(Strings_Pevneva.YOU_ARE_BANNED)
        reject()
      }).catch(() => {reject()})
    })
  }

  answerChanged = (value, id) => {
    let newAnswers = this.state.answers
    newAnswers[id] = value
    this.setState({answers: newAnswers})
  }
  
  render() {
    const that = this;

    return (
      <div id='ViewSurveyPage'>
        <div id='questionsList'>
          {this.state.questionsList.map(function(question) {
            return(
              <Question_Pevneva valueChanged={that.answerChanged} question={question}/>
            )
          })}
          <Box sx={{ paddingTop: 4 }} />
        
          <Button variant="outlined" onClick={this.onSaveAnswerClick}>Сохранить ответы</Button>
          <Box sx={{ paddingTop: 20 }} />
        </div>
      </div>
    )
  }
}