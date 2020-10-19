<%@ page import="domain.Word" %>
<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: SixthMagnitudeStar
  Date: 10/19/2020
  Time: 10:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>result page</title>
</head>
<body>
    <% ArrayList<Word> words = ((ArrayList<Word>) request.getAttribute("result")); %>
    <table>
        <tr>
            <th>Language</th>
            <th>Word</th>
            <th>Definition</th>
        </tr>
        <%
        for(int i = 0; i < words.size(); i++) {
                out.println("<tr>");
                out.println("<td>" + ((String)words.get(i).Language) + "</td>");
                out.println("<td>" + ((String)words.get(i).Word) + "</td>");
                out.println("<td>" + ((String)words.get(i).Definition) + "</td>");
                out.println("</tr>");
            }
        %>
    </table>
</body>
</html>
