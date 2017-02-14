<!doctype HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:url var="resourceUrl" value="/static" />
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title>Welcome to zomato</title>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous" />
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous" />
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" />
		<link href='https://fonts.googleapis.com/css?family=Source+Sans+Pro' rel='stylesheet' type='text/css' />
		<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
		<script src="${resourceUrl}/js/src/angular.min.js"></script>
		<script src="https://rawgit.com/dwmkerr/angular-modal-service/master/dst/angular-modal-service.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.7/angular-route.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.7/angular-animate.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.7/angular-sanitize.js"></script>
		<link href="${resourceUrl}/css/zomato.css" rel="stylesheet" />
		<script src="${resourceUrl}/js/src/ui-bootstrap-tpls-2.2.0.min.js"></script>
	</head>
	<body ng-app="app">
		<div ng-view></div>
		<script src="${resourceUrl}/js/src/app/restaurant/RestaurantDetailsController.js"></script>
		<script src="${resourceUrl}/js/src/app/restaurant/HeaderController.js"></script>
		<script src="${resourceUrl}/js/src/app/collection/CollectionController.js"></script>
		<script src="${resourceUrl}/js/src/app/index/BodyController.js"></script>
		<script src="${resourceUrl}/js/src/app/search/SearchCriteriaController.js"></script>
		<script src="${resourceUrl}/js/src/app/search/RecommandRestaurantController.js"></script>
		<script src="${resourceUrl}/js/src/app/global/BrokerService.js"></script>
		<script src="${resourceUrl}/js/src/app/global/HttpPromiseSerivce.js"></script>
		<script src="${resourceUrl}/js/src/app/global/RestaurantService.js"></script>
		<script src="${resourceUrl}/js/src/app/global/UtilService.js"></script>
		<script src="${resourceUrl}/js/src/app/global/SearchCriteria.js"></script>
		<script src="${resourceUrl}/js/src/app/global/UserService.js"></script>
		<script src="${resourceUrl}/js/src/app/app.js"></script>
	</body>
</html>
