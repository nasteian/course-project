import '../App.css'
import React from 'react'

export default function Question_Pevneva(props) {
  return (
    <div className='question'>
      <div className='questionTitle'>{props.question['wording']}</div>
    </div>
  )
}