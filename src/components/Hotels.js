
import React from 'react';
import { Link }  from 'react-router';

import Messages from './Messages';
import Header from './Header-home';
import Footer from './Footer';

export default React.createClass({
  getInitialState() {
    fetch('http://localhost:8080/hotels.json')
    .then(function(response) {
      if (response.status !== 200) {
        console.log('Looks like there was a problem. Status Code: ' + response.status);
        return;
      }
      return response.json()
    }).then(function(json) {
      console.log('parsed json', json)
    }).catch(function(ex) {
      console.log('parsing failed', ex)
    })
    return {};
  },
  render: function() {
    return (
      <div>
        <Messages message={{type:'info',text:'lol'}}/>
        <div >
          <form className="clear" method="GET">
            <div className="container">
              <br/>
              <div className="row">
                <div className="four columns">
                  <label>Dates</label>
                </div>
                <div className="four columns">
                  <div className="isle-1-h">
                    <input name="arrival" className="clear" type="date"/>
                  </div>
                </div>
                <div className="four columns">
                  <div className="isle-1-h">
                    <input name="departure" className="clear" type="date"/>
                  </div>
                </div>
              </div>
              <br/>
              <hr className="clear"/>
              <br/>
            </div>
            <div className="container">
              <div className="row">
                <div className="four columns">
                  <label>Room Type</label>
                </div>
                <div className="eight columns">
                  <div className="isle-1-h">
                    <select name="roomtype" className="clear">
                      <option defaultValue disabled="">Room Type</option>
                    </select>
                  </div>
                </div>
              </div>
              <br/>
            </div>
            <div className="secondary">
              <div className="container isle-1-v">
                <button type="submit">Filter</button>
                <span className="right"><b><span></span> </b>Hotels Found</span>
              </div>
            </div>
          </form>
          <div className="secondary">
            <div className="container">
              <div className="media hotel-list">
                <img src="https://placehold.it/150x150" alt="" className="media__img"/>
                <div className="media__body">
                  <div className="row">
                    <div className="nine columns">
                      <h5><a className="a-none" text="${hotel.name}" href="@{|/hotels/${hotel.id}|}"></a> <span className="'star-' + ${hotel.rating}"></span></h5>
                      <p className="clearmargin"> Adress: <span text="${hotel.address}"/></p>
                      <p className="clearmargin"> Category: <span text="${hotel.category}"/></p>
                    </div>
                    <div className="three columns">
                      comments
                    </div>
                  </div>
                  <div className="row bottomrow">
                    <div className="eight columns">
                    </div>
                    <div className="right four columns">
                      <a href="@{|/hotels/${hotel.id}|}" className="gohotel clearmargin button button-full button-primary">Book now</a>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
});
