import '../App.css'
import React from 'react'
import { MenuItem, TextField } from '@mui/material'
import QuestionTypes_Pevneva from '../settings/QuestionTypes_Pevneva'
import Strings_Pevneva from '../settings/Strings_Pevneva'

export default function Question_Pevneva(props) {
  const booleanAnswers = [{
      label: 'Да',
      value: 'true'
    }, {
      label: 'Нет',
      value: 'false'
  }]
  const variantAnswers = props.question.variants

  let defaultValue = null
  switch(props.question.type) {
    case QuestionTypes_Pevneva.TEXT:
      defaultValue = ''
      break

    case QuestionTypes_Pevneva.BOOLEAN:
      defaultValue = booleanAnswers[0].value
      break
      
    case QuestionTypes_Pevneva.VARIANT:
      defaultValue = variantAnswers[0]
      break
  }

  const [value, setValue] = React.useState(defaultValue)
  const [firstUpdate, setFirstUpdate] = React.useState(true)

  if (firstUpdate) {
    setFirstUpdate(false)
    props.valueChanged(defaultValue, props.question.id)
  }

  function valueChanged(e) {
    setValue(e.target.value)
    props.valueChanged(e.target.value, props.question.id)
  }

  function renderSwitch(param) {
    switch(param) {
      case QuestionTypes_Pevneva.TEXT:
        return (
          <>
            <TextField className='answerField'
              label={props.question.wording}
              value={value}
              onChange={(e) => {valueChanged(e)}} />
          </>
        )

      case QuestionTypes_Pevneva.BOOLEAN:
        return (
          <>
            <TextField className='formField' margin='dense'
              select
              required
              label=''
              value={value}
              onChange={(e) => {valueChanged(e)}}
              variant='standard'>
              {booleanAnswers.map((option) => (
                <MenuItem key={option.value} value={option.value}>
                  {option.label}
                </MenuItem>
              ))}
            </TextField>
          </>
        )

      case QuestionTypes_Pevneva.VARIANT:
        return (
          <>
            <TextField className='formField' margin='dense'
              select
              required
              label=''
              value={value}
              variant='standard'
              onChange={(e) => {valueChanged(e)}} >
              {variantAnswers.map((item) => (
                <MenuItem key={item} value={item}>
                  {item}
                </MenuItem>
              ))}
            </TextField>
          </>
        )

      default:
        return (<></>);
    }
  }
  
  return (
    <div className='question'>
      <div className='questionTitle'>{props.question.wording}</div>
      {renderSwitch(props.question.type)}
    </div>
  )
}