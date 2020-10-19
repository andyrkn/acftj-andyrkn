<%--
  Created by IntelliJ IDEA.
  User: SixthMagnitudeStar
  Date: 10/19/2020
  Time: 9:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        .container{
            display:flex;
            flex-direction: column;
            width: 100%;
            height: auto;
        }
    </style>
    <title>Hello</title>
</head>
<body>
    <div class="container">
        <form class="container" action="/words" method="POST">
            <label>Language</label>
            <input placeholder="Language" name="Language">

            <label>Word</label>
            <input placeholder="Word" name="Word">

            <label>Definition</label>
            <input placeholder="Definition" name="Definition">

            <input id="form-submit"  type="submit" value="Submit" disabled="true">
        </form>
        <div>
            <p id="captcha-input"></p>
            <input id="captcha-value">
            <button onclick="fillCaptcha()">Check</button>
        </div>
    </div>
</body>
<script src="input.js"></script>
</html>
