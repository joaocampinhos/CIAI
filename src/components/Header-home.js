import React from 'react';
import { Link }  from 'react-router';
import auth from '../services/auth';

export default React.createClass({
  render: function() {
    const logged = auth.loggedIn()
    return (
      <header>
        <div className="cover">
          <div className="container">
            <div className="row">
              <Link to="/"><h3 className="logo six columns">Hotelr</h3></Link>
              <div className="login six columns">
                {logged ? (
                  <Link to="/logout">Log out</Link>
                  ) : (
                  <Link to="/login">Log in</Link>
                  )}
              </div>
            </div>
            <p className="copy">
              Find the perfect hotel with us
              <br/>
              <Link to={`/hotels`} className="button">Start now</Link>
            </p>
          </div>
        </div>
      </header>
    );
  }
});
