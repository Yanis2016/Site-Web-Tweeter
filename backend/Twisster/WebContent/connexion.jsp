<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Connexion</title>
        <link type="text/css" rel="stylesheet" href="form.css" />
    </head>
    <body>
       <div>
            <form method="post" action="login">
                <fieldset>
                    <legend>Connexion</legend>
			   		<p>
			            <label for="login">login : <span class="requis">*</span> </label>
			            <input type="email" name="login" id="login" />
			        </p>
			        <p>
			            <label for="password">password :  <span class="requis">*</span></label>
			            <input type="password" name="password" id="password" />
			        </p>
                </fieldset>
                <input type="submit" value="connexion"  />
            </form>
        </div>
    </body>
</html>