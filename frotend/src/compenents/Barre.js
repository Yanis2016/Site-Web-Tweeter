import React, { Component } from 'react'
import InlineLogin from './InlineLogin'
import { Layout, Menu, Input } from 'antd'
import Logout from './Logout';
import MyLogo from './Logo'

const Search = Input.Search;
const { Header } = Layout;

export default class MenuBarre extends Component {

    /*
        traiter la recherche 
        traiter la connexion
        traiter les menusItems 
    */

    render() {
        if (this.props.connecter === false)
            return (
                <div className="container-fluid">
                    <Layout>
                        <Header>
                            <div className="float-left">
                                <MyLogo>
                                </MyLogo>
                            </div>
                            <div className="d-flex">
                                <Menu
                                    theme="dark"
                                    defaultSelectedKeys={['1']}
                                    mode="horizontal"
                                    style={{ lineHeight: '66px' }}
                                >
                                    <Menu.Item key="1">Home</Menu.Item>
                                    <Menu.Item key="2">About</Menu.Item>
                                    <Menu.Item key="3">Contact</Menu.Item>
                                </Menu>
                                <div className="ml-auto p-1 align-top">
                                    <InlineLogin
                                        getConnected={this.props.getConnected}
                                        setRedirect={this.props.setRedirect}
                                    ></InlineLogin>
                                </div>
                            </div>
                        </Header>
                    </Layout>
                </div>
            )
        else
            return (
                <div className="container-fluid" style={{ "marginTop": "1%" }}>
                    <Layout className="layout" >
                        <Header style={{ "background": "rgb(0, 21, 41)" }}>
                            <div className="row align-middle">
                                <div className="logo-search-bar">
                                    <MyLogo>
                                    </MyLogo>
                                </div>
                                <Search
                                    className="col-lg-4 ml-auto mr-auto align-bottom "
                                    placeholder="search"
                                    onSearch={value => console.log(value)}
                                    style={{ height: 25, "marginTop": "1%" }}
                                />
                                <div>
                                    <Logout setLogout={this.props.setLogout} ></Logout>
                                </div>
                            </div>
                        </Header>
                    </Layout>
                </div>
            )

    }

}