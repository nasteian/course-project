import '../App.css'
import React from 'react'
import Cookies from 'js-cookie'
import Cookies_Pevneva from '../settings/Cookies_Pevneva'
import RequestUrls_Pevneva from '../settings/RequestUrls_Pevneva'
import Question_Pevneva from '../components/Question_Pevneva'

export default class ViewSurveyPage_Pevneva extends React.Component {
  constructor(props) {
    super(props)

    this.state = {
      questionsList: []
    }

    this.loadQuestions()
  }

  loadQuestions = async () => {
    const request_body = {
      'login': Cookies.get(Cookies_Pevneva.LOGIN),
      'session_id': Cookies.get(Cookies_Pevneva.SESSION_ID)
    }
  
    let { id } = this.props.params
    let questions = await fetch(RequestUrls_Pevneva.SURVEY + '/' + id + RequestUrls_Pevneva.GET_ALL_QUESTIONS, {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify(request_body)
    })
    questions = await questions.json()

    this.setState({questionsList: questions})
  }
  
  render() {
    return (
      <div id='ViewSurveyPage'>
        <div id='questionsList'>
          {this.state.questionsList.map(function(question) {
            return(
              <Question_Pevneva question={question}/>
            )
          })}
        </div>
      </div>
    )
  }
}