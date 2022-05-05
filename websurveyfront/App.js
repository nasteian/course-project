import './App.css'
import React, { useRef, useEffect, useState } from 'react'
import RegisterPage_Pevneva from './pages/RegisterPage_Pevneva'
import RegisterSuccessPage_Pevneva from './pages/RegisterSuccessPage_Pevneva'
import LoginPage_Pevneva from './pages/LoginPage_Pevneva'
import { BrowserRouter, Route, Routes, Navigate, useLocation } from 'react-router-dom'
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
import { Toaster } from 'react-hot-toast'
import ModifySurveyPage_Pevneva from './pages/ModfiySurveyPage_Pevneva'
import RecoveryPage_Pevneva from './pages/RecoveryPage_Pevneva'
import StatisticsSurveyPage_Pevneva from './pages/StatisticsSurveyPage_Pevneva'
import BanPage_Pevneva from './pages/BanPage_Pevneva'

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

  const ParamsWrapper = (props) => {
    const params = useParams();
    return <props.element {...{...props, params: params} } />
  }
  
  verifySession()

  return (
    <>
      <div> <Toaster position='bottom-center' reverseOrder={false} /> </div>
      <Routes> 
        <Route path={PageUrls_Pevneva.SURVEYS_UNCOMPLETED} element={<UncompletedSurveysPage_Pevneva />} />
        <Route path={PageUrls_Pevneva.SURVEYS_MY} element={<MySurveysPage_Pevneva />} />
        
        <Route path={PageUrls_Pevneva.BAN} element={<BanPage_Pevneva />} />
        
        <Route path={PageUrls_Pevneva.RECOVERY} element={<RecoveryPage_Pevneva />} />

        <Route path={PageUrls_Pevneva.SURVEYS + '/*'} element={<Navigate to={PageUrls_Pevneva.SURVEYS_UNCOMPLETED} replace />} />
        
        <Route path={PageUrls_Pevneva.SURVEY + '/:id'} element={<ParamsWrapper element={ViewSurveyPage_Pevneva} />} />
        <Route path={PageUrls_Pevneva.SURVEY + '/:id' + PageUrls_Pevneva.MODIFY} element={<ParamsWrapper element={ModifySurveyPage_Pevneva} />} />
        <Route path={PageUrls_Pevneva.SURVEY + '/:id' + PageUrls_Pevneva.STATISTICS} element={<ParamsWrapper element={StatisticsSurveyPage_Pevneva} />} />

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