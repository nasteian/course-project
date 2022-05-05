import '../App.css'
import React from 'react'
import Cookies from 'js-cookie'
import Cookies_Pevneva from '../settings/Cookies_Pevneva'
import RequestUrls_Pevneva from '../settings/RequestUrls_Pevneva'
import SurveyCard_Pevneva from '../components/SurveyCard_Pevneva'
import TextField from '@mui/material/TextField'
import { Box, IconButton } from '@mui/material'
import GavelIcon from '@mui/icons-material/Gavel'
import GppGoodIcon from '@mui/icons-material/GppGood'
import toast from 'react-hot-toast'
import Strings_Pevneva from '../settings/Strings_Pevneva'

export default function BanPage_Pevneva() {
  const[users, setUsers] = React.useState([])
  const[firstUpdate, setFirstUpdate] = React.useState(true)

  if (firstUpdate) {
    setFirstUpdate(false)
    loadUsers()
  }

  async function loadUsers() {
    let usersList = await fetch(RequestUrls_Pevneva.GET_ALL_USERS, {
      method: 'GET',
      headers: {'Content-Type': 'application/json'}
    })
    usersList = await usersList.json()
    
    setUsers(usersList)
  }

  function RenderUser(props) {
    const[banned, setBanned] = React.useState(props.user.banned)

    function onUnbanClick() {
      toast.promise(
        Unban(), {
           loading: Strings_Pevneva.UNBANING,
           success: <b>{props.user.login + Strings_Pevneva.UNBANNED}</b>,
           error: <b>{Strings_Pevneva.OCCURED_ERROR}</b>
         }
      )
    }
    
    function onBanClick() {
      toast.promise(
        Ban(), {
            loading: Strings_Pevneva.BANING,
            success: <b>{props.user.login + Strings_Pevneva.BANNED}</b>,
           error: <b>{Strings_Pevneva.OCCURED_ERROR}</b>
         }
      )
    }

    function Ban() {
      return new Promise((resolve, reject) => {
        const request_body = {
          'login': Cookies.get(Cookies_Pevneva.LOGIN),
          'session_id': Cookies.get(Cookies_Pevneva.SESSION_ID),
          'user': props.user.login
        }
      
        fetch(RequestUrls_Pevneva.BAN, {
          method: 'POST',
          headers: {'Content-Type': 'application/json'},
          body: JSON.stringify(request_body)
        }).then(response => {
          if (response.status === 200) { 
            resolve()
            setBanned(true)
          }
          reject()
        }).catch(() => {reject()})
      })
    }

    function Unban() {
      return new Promise((resolve, reject) => {
        const request_body = {
          'login': Cookies.get(Cookies_Pevneva.LOGIN),
          'session_id': Cookies.get(Cookies_Pevneva.SESSION_ID),
          'user': props.user.login
        }
      
        fetch(RequestUrls_Pevneva.UNBAN, {
          method: 'POST',
          headers: {'Content-Type': 'application/json'},
          body: JSON.stringify(request_body)
        }).then(response => {
          if (response.status === 200) { 
            resolve()
            setBanned(false)
          }
          reject()
        }).catch(() => {reject()})
      })
    }

    return (
      <div className='user'>
        <div className='loginText'>{props.user.login}</div>
        <Box sx={{ paddingTop: 1 }} />
        {(banned === true ? 
          <IconButton onClick={onUnbanClick} component='span'>
            <GppGoodIcon />
          </IconButton> 
        : 
          <IconButton onClick={onBanClick} component='span'>
            <GavelIcon />
          </IconButton>
        )}
      </div>
    )
  }
  
  return (
    <div id='BanPage'>
      <div id='usersList'>
        {users.map(function(user) {
          return (<RenderUser user={user}/>)
        })}
        <Box sx={{ paddingTop: 20 }} />
      </div>
    </div>
  )
}