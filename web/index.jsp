<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Large File Upload</title>
  </head>
  <body>
    <div>${requestScope.message}</div>
    <form enctype="multipart/form-data" method="post" action="upload">
      <label for="upload">Select a file to upload: </label>
      <input type="file" name="upload" id="upload" />
      <input type="submit" value="Upload" />
    </form>
  </body>
</html>
