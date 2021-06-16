<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ page import="yun.YunUploadUtil" %>
<%
    HashMap<String,Object> map = YunUploadUtil.uploadFile(request,response);
%>
