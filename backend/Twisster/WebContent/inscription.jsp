<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta charset="utf-8" />
        <title>Inscription</title>
        <link type="text/css" rel="stylesheet" href="inc/style.css" />
    </head>
    <body>
        <div>
            <form method="POST" action="CreateUser">
                <fieldset>
                    <legend>Informations client</legend>
    				
    				<label for="login">login <span class="requis">*</span></label>
                    <input type="email" id="login" name="login" value="" size="20" maxlength="20" />
                    <br />
                    
                    <label for="nomClient">Nom <span class="requis">*</span></label>
                    <input type="text" id="nomClient" name="nom" value="" size="20" maxlength="20" />
                    <br />
                    
                    <label for="prenomClient">Prénom </label>
                    <input type="text" id="prenomClient" name="prenom" value="" size="20" maxlength="20" />
                    <br />
    
                    
                    <label for="prenomClient">sex </label>
                    <input type="text" id="prenomClient" name="sex" value="" size="20" maxlength="20" />
                    <br />
    				
    				<label for="prenomClient">Date Naissance </label>
                    <input type="text" id="prenomClient" name="birth_date" value="" size="20" maxlength="20" />
                    <br />
    
   					
   					 <label for="password">passwor <span class="requis">*</span></label>
                    <input type="password" id="password" name="password" value="" size="20" maxlength="20"/>
                    <br />
                </fieldset>
                <input type="submit" value="Créer"  />
                <input type="reset" value="Remettre à zéro" /> <br />
            </form>
        </div>
    </body>
</html>