import '../App.css'
import React from 'react'
import List from '@mui/material/List' 
import ListItem from '@mui/material/ListItem' 
import ListItemIcon from '@mui/material/ListItemIcon' 
import ListItemText from '@mui/material/ListItemText'
import AssessmentIcon from '@mui/icons-material/Assessment'
import AutoAwesomeMosaicIcon from '@mui/icons-material/AutoAwesomeMosaic'
import AddModeratorIcon from '@mui/icons-material/AddModerator'
import { useNavigate } from 'react-router-dom'
import PageUrls_Pevneva from '../settings/PageUrls_Pevneva'

let currentIndex = 0

export default function LeftBar_Pevneva() {
  const navigate = useNavigate()

  function onClick(index, href) {
    if (index === currentIndex) return
    currentIndex = index
    console.log(currentIndex)
    navigate(href)
  }
  
  return (
    <div id='leftBar'>
      <List> 
        {[{text: 'Непройденые опросы', icon: <AutoAwesomeMosaicIcon />, href: PageUrls_Pevneva.SURVEYS_UUNCOMPLETED},
          {text: 'Мои опросы', icon: <AddModeratorIcon />, href: PageUrls_Pevneva.SURVEYS_MY}
          ].map((item, index) => (
          <ListItem button onClick={() => {onClick(index, item.href)}} key={item.text} selected={index === currentIndex}>
            <ListItemIcon>{item.icon}</ListItemIcon> 
            <ListItemText primary={item.text} />
          </ListItem>
        ))}
      </List>
    </div>
  )
}