import React, {Component} from 'react';

import axios from 'axios';
const _http = axios.create({
  baseURL: 'http://127.0.0.1:8080',
  headers: {
    'content-type': 'application/json;charset=utf-8'
  },
  withCredentials: true
});

const style = {
  container: {
    width: '400px'
  },
  input: {
    width: '100%',
    padding: '10px',
    display: 'block'
  }
};

class App extends Component {

  constructor(props) {
    super(props);
    this.state = {
      isLogin: false,
      username: 'test',
      password: '1234',
      error: false,
      principal: null,
      roles: []
    }
  }

  componentDidMount() {
    this.handleCheckLogin();
  }

  handleCheckLogin = async () => {
    const res = await _http.post('/api/check_login');
    if ( res.data !== false ) {
      this.setState({
        isLogin: true,
        username: res.data
      })
    } else {
      this.setState({
        isLogin: false
      })
    }
  };

  handleLogin = async (e) => {
    e.preventDefault();

    const user = {
      username: 'test',
      password: '1234'
    };
    const res = await _http.post('/api/login', user);

    if ( res.status === 200 ) {
      this.setState({
          ...this.state,
        error: false,
        isLogin: true
      });

      this.handleErrorMessage(false);
    } else {
      this.handleErrorMessage(true);
    }
  };

  handleGetResource = async () => {
    const res = await _http.get('/api/test');

    if ( res.status === 200 ) {
      this.setState({
        principal: res.data
      });
    }
  };

  getUserRoles = async () => {
    try {
      const res = await _http.get('/api/roles');
      console.log(res);
      const { authorities } = res.data;

      this.setState({
        ...this.state,
        roles: this.state.roles.concat(authorities)
      });

      this.handleErrorMessage(false);
    } catch (e) {
      this.handleErrorMessage(true);
    }
  };

  handleErrorMessage = bool => {
    this.setState({ error: bool });
  };

  handleFormData = e => {
    e.preventDefault();
    this.setState({
      [e.target.name]: e.target.value
    })
  };

  handleLogout = async () => {

    const res = await _http.post('/api/logout');

    if ( res.status === 200 ) {
      this.setState({
        isLogin: false,
        roles: []
      })
    }
  };

  render() {
    return (
      <div style={style.container}>
        <fieldset>
          <form method="post" onSubmit={this.handleLogin}>
            <input style={style.input} type="text" name="username" value={this.state.username} onChange={this.handleFormData}/>
            <input style={style.input} type="password" name="password" value={this.state.password} onChange={this.handleFormData}/>

            <button onClick={this.handleLogin}>로그인</button>
          </form>
        </fieldset>
        <button onClick={this.handleGetResource}>private</button>
        <button onClick={this.getUserRoles}>roles</button>
        <button onClick={this.handleLogout}>로그아웃</button>

        {this.state.error && (
            <h4>아직 로그인 되어있지 않습니다. 먼저 로그인을 해주세요.</h4>
        )}

        {
          this.state.isLogin
              ? <p>현재 로그인 유저는 {this.state.username} 입니다.</p>
              : <p>지금은 로그인 되어있지 않습니다.</p>
        }

        <ul>
          {this.state.roles.map((role, index) => (
              <li key={index}>
                {role.authority}
              </li>
          ))}
        </ul>

      </div>
    );
  }
}

export default App;
