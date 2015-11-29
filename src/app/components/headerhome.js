'use strict';

var React = require('react');
var Link = require('react-router').Link;

module.exports = React.createClass({

  render: function() {
    return (
      <header>
        <div className="cover">
          <div className="container">
            <div className="row">
              <Link to={`/`}><h3 className="logo six columns">Hotelr</h3></Link>
              {/*ver se ta logado*/}
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
