import './App.css'
import React, { useRef, useEffect, useState } from 'react'
import RegisterPage_Pevneva from './pages/RegisterPage_Pevneva'
import RegisterSuccessPage_Pevneva from './pages/RegisterSuccessPage_Pevneva'
import LoginPage_Pevneva from './pages/LoginPage_Pevneva'
import { BrowserRouter, Route, Routes, Navigate, useLocation } from "react-router-dom"
import PageUrls_Pevneva from "./settings/PageUrls_Pevneva"
import HomePage_Pevneva from './pages/HomePage_Pevneva'
import MySurveysPage_Pevneva from './pages/MySurveysPage_Pevneva'
import LeftBar_Pevneva from './components/LeftBar_Pevneva'
import Cookies_Pevneva from './settings/Cookies_Pevneva'
import RequestUrls_Pevneva from './settings/RequestUrls_Pevneva'
import Cookies from 'js-cookie'
import TopBar_Pevneva from './components/TopBar_Pevneva'
import UncompletedSurveysPage_Pevneva from './pages/UncompletedSurveysPage_Pevneva'
import ViewSurveyPage_Pevneva from './pages/ViewSurveyPage_Pevneva'
import { useParams } from 'react-router-dom'

function Root() {
  const topBar = useRef(null)
  const location = useLocation()

  async function verifySession() {
    let login = Cookies.get(Cookies_Pevneva.LOGIN)
    let sessionId = Cookies.get(Cookies_Pevneva.SESSION_ID)
    
    let request_body = {
      'login': login,
      'session_id': sessionId
    }
  
    let sessionIsGood = false
    if (login != null && sessionId != null) {
      sessionIsGood = await fetch(RequestUrls_Pevneva.VERIFY_SESSION, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(request_body)
      })
      sessionIsGood = await sessionIsGood.json()
    }

    Cookies.set(Cookies_Pevneva.AUTHORIZED, sessionIsGood)
  }

  const ViewSurveyPageWrapper = (props) => {
    const params = useParams();
    return <ViewSurveyPage_Pevneva {...{...props, params: params} } />
  }

  verifySession()

  return (
    <>
      <Routes> 
        <Route path={PageUrls_Pevneva.SURVEYS_UUNCOMPLETED} element={<UncompletedSurveysPage_Pevneva />} />
        <Route path={PageUrls_Pevneva.SURVEYS_MY} element={<MySurveysPage_Pevneva />} />

        <Route path={PageUrls_Pevneva.SURVEYS + '/*'} element={<Navigate to={PageUrls_Pevneva.SURVEYS_UUNCOMPLETED} replace />} />
        
        <Route path={PageUrls_Pevneva.SURVEY + '/:id'} element={<ViewSurveyPageWrapper />} />

        <Route path={PageUrls_Pevneva.REGISTER} element={<RegisterPage_Pevneva />} />
        <Route path={PageUrls_Pevneva.REGISTER_SUCCESS} element={<RegisterSuccessPage_Pevneva />} />
        <Route path={PageUrls_Pevneva.LOGIN} element={<LoginPage_Pevneva />} />
        <Route path={PageUrls_Pevneva.HOME} element={<HomePage_Pevneva />} />
      </Routes>
      
      <Routes>
        <Route path={PageUrls_Pevneva.SURVEYS + '/*'} element={<TopBar_Pevneva doNotShowSurveysButton/>} />
        <Route path='/*' element={<TopBar_Pevneva />} />
      </Routes>
      
      <Routes>
        <Route path={PageUrls_Pevneva.SURVEYS + '/*'} element={<LeftBar_Pevneva />} />
      </Routes>
    </>
  )
}

export default function App() {  
  return (
    <BrowserRouter>
      <Root />
    </BrowserRouter>
  )
}