
import React from 'react';
import { Link }  from 'react-router';

import Header from './Header';
import Footer from './Footer';

export default React.createClass({
  render: function() {
    return (
      <div>
        <Header/>
        <div className="hotel-cover" style={{backgroundImage: 'url(https://images.unsplash.com/photo-1443479579455-1860f114bf77)'}}> </div>
        <div className="container row">
          <div className="seven columns">
            <div>
              <p className="clearmargin text-center isle-1-v">
                <span className="star-3"></span>
                <br/>
                <span className="h3 thin uppercase">
                  NOME
                </span>
              </p>
            </div>
            <h5 className="clearmargin thin">
              Category
            </h5>
            <p><span>categoria</span></p>
            <h5 className="clearmargin thin">
              Adress
            </h5>
            <p> <span>adress</span></p>
            <h5 className="clearmargin thin">
              Photos
            </h5>
            <div className="photo-thumb"><img src="https://placehold.it/80x80"/> </div> 
            <div className="photo-thumb"><img src="https://placehold.it/80x80"/> </div> 
            <div className="photo-thumb"><img src="https://placehold.it/80x80"/> </div> 
            <div className="photo-thumb"><img src="https://placehold.it/80x80"/> </div> 
            <div className="photo-thumb"><img src="https://placehold.it/80x80"/> </div> 
            <div className="photo-thumb"><img src="https://placehold.it/80x80"/> </div> 
            <div className="photo-thumb"><img src="https://placehold.it/80x80"/> </div> 
            <h4 className="thin">Manager </h4>
            <div className="media comment">
              <img src="http://www.gravatar.com/avatar/b4e15cc67a93380cbf8f2a23b355f380" alt="" className="avatar media__img"/>
              <div className="media__body">
                <h5><span text="hotel.manager.name"></span></h5>
                email: <span text="hotel.manager.email"></span>
              </div>
            </div>
          </div>
          <div className="five columns">
            <br/>
            <form action="'/hotels/'+hotel.id+'/bookings'" method="POST">
              <label>Check-in</label>
              <input name="arrival" className="clear" type="date"/>
              <label>Check-out</label>
              <input name="departure" className="clear" type="date"/>
              <label>Room type</label>
              <select name="roomtype">
                <option value="room.type.id" text="room.type.name"/>
              </select>
              <h4>Preço: <span id="price"></span>€</h4>
              <button className="button button-primary" type="submit">Book</button>
            </form>
          </div>
        </div>
        <div className="container">
          <hr/>
          <h3 className="thin">Rooms</h3>
          <table className="u-full-width">
            <thead>
              <tr>
                <th>Room Type</th>
                <th>Price</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td text="room.type.name"></td>
                <td text="room.price+'€'"></td>
              </tr>
            </tbody>
          </table>
        </div>
        <Footer/>
      </div>
    );
  }
});
/*
   <br/>
   <a href="'/hotels/'+${hotel.id}+'/edit'" th:if="${#authentication.name == hotel.manager.email }" className="button button-primary button-full">Edit Hotel</a>
   <form sec:authorize="isAnonymous()" th:action="'/hotels/'+${hotel.id}+'/bookings'" method="POST">
   <label>Check-in</label>
   <input name="arrival" th:value="${param.arrival[0]}" th:unless="${param.arrival == null}" className="clear" type="date"/>
   <input name="arrival" th:value="${#dates.format(#calendars.createToday(), 'yyyy-MM-dd')}" th:unless="${param.arrival != null}" className="clear" type="date"/>
   <label>Check-out</label>
   <input name="departure" th:value="${param.departure[0]}" th:unless="${param.departure == null}" className="clear" type="date"/>
   <input name="departure" th:value="${#dates.format(#calendars.createToday(), 'yyyy-MM-dd')}" th:unless="${param.departure != null}" className="clear" type="date"/>
   <label>Room type</label>
   <select name="roomtype">
   <option th:each="room: ${hotel.rooms}" data-th-attr="data-price=${room.price}" th:selected="${param.roomtype[0] == room.type.name+''}" th:value="${room.type.id}" th:unless="${param.roomtype == null}" th:text="${room.type.name}"/>
   <option th:each="room: ${hotel.rooms}" data-th-attr="data-price=${room.price}" th:value="${room.type.id}" th:unless="${param.roomtype != null}" th:text="${room.type.name}"/>
   </select>
   <h4>Preço: <span id="price"></span>€</h4>
   <button className="button button-primary" type="submit">Book</button>
   </form>
   */
