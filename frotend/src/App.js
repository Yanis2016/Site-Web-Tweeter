
import React from "react"



import MainPage from './MainPage';

import { Route, Switch } from "react-router-dom"
import NotFound from './compenents/NotFound'
import Mur from './compenents/Mur'
import SignUp from './compenents/SignUp';
import NavBar from './compenents/NavBar';
import SearchBar from "./compenents/SearchBar"
import Login from './compenents/Login'
import Logout from "./compenents/Logout"
import MyLogo from "./compenents/Logo"
import InlineLogin from "./compenents/InlineLogin"
import NavigationPannel from "./compenents/NavigationPannel"
import PageProfile from "./compenents/ProfilePage"
import CenterContainer from "./compenents/CenterContainer"
import Barre from "./compenents/Barre"
import GestionAffichage from "./GestionAffichage"

const App = () =>(
    <div>
        <Switch>
            <Route exact path='/'><GestionAffichage page={"signin"}/> </Route>
            <Route exact path='/signup'><GestionAffichage page={"signup"}/></Route>
            <Route exact path='/signin'><GestionAffichage page={"signin"}/></Route>
            <Route exact path='/home'><GestionAffichage page={"home"}/></Route>
            <Route exact path='/profile'><GestionAffichage page={"profile"}/></Route>
            <Route path="*" component={NotFound}></Route>
        </Switch>
    </div>
    
)

export default App 