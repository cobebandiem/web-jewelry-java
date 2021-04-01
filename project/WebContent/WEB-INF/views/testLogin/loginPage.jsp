<%@ page 
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
  <style>
    body{
      height:2000px;
    }
    .app{
      width: 100%;
      height:100vh;
    }
    .container{
      height: 100%;
      width: 500px;
    }
  </style>
</head>
<body>
	<div class="app">
    <div class="container">
      <h2>Vertical (basic) Form</h2>
      <form id="form-1">
        <div class="form-group">
          <label for="fullname">Name:</label>
          <input type="text" class="form-control" id="fullname" placeholder="Enter name" name="fullname">
          <span class="for-message invalid-feedback">dsadasdasd</span>
        </div>
        <div class="form-group">
          <label for="email">Email:</label>
          <input type="email" class="form-control" id="email" placeholder="Enter email" name="email">
          <span class="for-message invalid-feedback"></span>
        </div>
        <div class="form-group">
          <label for="password">Password:</label>
          <input type="password" class="form-control" id="password" placeholder="Enter password" name="password">
          <span class="for-message invalid-feedback"></span>
        </div>
        <div class="form-group">
          <label for="conform-password">Email:</label>
          <input type="password" class="form-control" id="conform-password" placeholder="Enter conform password" name="comform-password">
          <span class="for-message invalid-feedback"></span>
        </div>
        <div class="form-group">
          <label for="">Gender:</label><br>
          <label for="">Nam:</label>
          <input type="radio" name="gender" value="male" class="form-control">
          <label for="">Nữ:</label>
          <input type="radio" name="gender" value="female" class="form-control">
          <span class="for-message invalid-feedback"></span>
        </div>
        <div class="form-group">
          <label for="">Age:</label><br>
          <label for="">1:</label>
          <input type="checkbox" name="age" value="1" class="form-control">
          <label for="">2:</label>
          <input type="checkbox" name="age" value="2" class="form-control">
          <label for="">3:</label>
          <input type="checkbox" name="age" value="3" class="form-control">
          <span class="for-message invalid-feedback"></span>
        </div>
        <div class="form-group">
          <input type="file" name="file" id="">
        </div>
        <div class="form-group">
          <input type="submit" class="form-control btn-primary" value="Submit">
        </div>
      </form>
    </div>
  </div>
  <script src="assets/js/Validator.js"></script>
  <script>
    Validator({
      form:'#form-1',
      messageElement:'.for-message',
      rules:[
        Validator.isRequired('#fullname','Vui lòng nhập đầy đủ họ tên của bạn'),
        Validator.isRequired('#email'),
        Validator.isEmail('#email'),
        Validator.isRequired('#password'),
        Validator.isPassword('#password',6),
        Validator.isRequired('#conform-password'),
        Validator.isConformed('#conform-password',function(){
          return document.querySelector('#form-1 #password').value;
        },'Mật khẩu nhập lại không chính xác'),
        Validator.isRequired('input[name="gender"]'),
        Validator.isRequired('input[name="age"]')
      ],
      onSubmit: function(data){
        //Call_API
        console.log(data);
      }
    })
  </script>
</body>
</html>