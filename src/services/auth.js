class Auth {

  login(form, cb) {
    cb = arguments[arguments.length - 1]
    if (localStorage.token) {
      if (cb) cb(true)
      this.onChange(true)
      return
    }
    log(form, (res) => {
      console.log(res);
      if(res.cookie) {
        localStorage.token = res.cookie.value;
        if (cb) cb(true, res.message);
        this.onChange(true)
      }
      else {
        if (cb) cb(false, res.message);
        this.onChange(false)
      }
    });
  }

  getToken() {
    return localStorage.token
  }

  logout(cb) {
    delete localStorage.token
    if (cb) cb()
    this.onChange(false)
  }

  loggedIn() {
    return !!localStorage.token
  }

  onChange() {}
}

export default new Auth()

function pretendRequest(email, pass, cb) {
  setTimeout(() => {
    if (email === 'joe@example.com' && pass === 'password1') {
      cb({
        authenticated: true,
        token: Math.random().toString(36).substring(7)
      })
    } else {
      cb({ authenticated: false })
    }
  }, 0)
}

function log(form, cb) {
  setTimeout(() => {
    fetch('http://localhost:8080/login', {
      method: 'post',
      body: form
    })
    .then(function(response) {
      return response.json()
    }).then(function(json) {
      cb(json)
    }).catch(function(err) {})
  }, 0)
}
