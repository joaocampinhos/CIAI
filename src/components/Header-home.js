import React from 'react';
import { Link }  from 'react-router';

export default React.createClass({
  render: function() {
    return (
      <header>
        <div className="cover">
          <div className="container">
            <div className="row">
              <a href="/"><h3 className="logo six columns">Hotelr</h3></a>
              <div className="login six columns">
                <Link to={`/login`}>Login</Link>
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
