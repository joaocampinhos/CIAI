
import React from 'react';
import auth from '../services/auth';
import { History, Link }  from 'react-router';

import Messages from './Messages';

export default React.createClass({

  mixins: [ History ],

  onSubmit(event) {

    event.preventDefault();

    const email = this.refs.email.value
    const pass = this.refs.pass.value

    auth.login(email, pass, (loggedIn) => {
      if (!loggedIn) {
        return this.history.replaceState({message: {value: 'Login error', type: 'error'}}, '/login')
      }

      const { location } = this.props

      if (location.state && location.state.nextPathname) {
        return this.history.replaceState({message: {value: 'Login successful', type: 'info'}}, location.state.nextPathname)
      } else {
        return this.history.replaceState({message: {value: 'Login successful', type: 'info'}}, '/')
      }
    })
  },
  /*

    //var that = this;
    //Auth.login(e.target)
    //Sucesso, manda para a pagina anterior com uma mensagem de sucesso
    //that.setState({message: json.message});
    //that.refs.flash.show();
    //that.history.goBack()
    //Deu merda, mostra a mensagem de erro
  },
  */
  render: function() {
    return (
      <div>
        <div className="container">
          <div className="log-form">
            <Link to="/"><img src="src/images/logo.png" className="logo-img"/></Link>
          </div>
          <div>
            <form onSubmit={this.onSubmit} name="form">
              <input ref="email" placeholder="Email" type="text" name="login"/>
              <input ref="pass" placeholder="Password" type="password" name="password"/>
              <button type="submit" className="button-full button-green">Log-in</button>
            </form>
          </div>
        </div>
      </div>
    );
  }
});
