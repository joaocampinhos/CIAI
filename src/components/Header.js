import React from 'react';
import { Link }  from 'react-router';
import auth from '../services/auth';

export default React.createClass({
  render: function() {
    const logged = auth.loggedIn()
    return (
      <header>
        <div className="container">
          <div className="nav row">
            <Link to="/"><h3 className="logo six columns">Hotelr</h3></Link>
            <div className="logged six columns">
              {logged ? (
                <Link className="button" to="/logout">Log out</Link>
                ) : (
                <Link className="button" to="/login">Log in</Link>
                )}
              </div>
            </div>
          </div>
        </header>
    );
  }
});
