var gulp = require('gulp');
var uglify = require('gulp-uglify');
var concat = require('gulp-concat');
var watch = require('gulp-watch');
var sass = require('gulp-sass');
var replace = require('gulp-replace');
var del = require('del');
var path = require('path');
var fs = require('fs');
var process = require("process");
var JS_FILES = ["js/src/*.js", "js/src/**/*.js"]
var HTML_FILES = ["html/*.html"]
var JS_DIR = "../../webapp/resources/js/src";
var SCSS_FILES = ["scss/*.scss"];
var CSS_DIR = "../../webapp/resources/css";
var HTML_DIR = "../../webapp/resources/html";

var dependency = {
    "dev": `<script src="\${resourceUrl}/js/src/app/restaurant/RestaurantDetailsController.js"></script>
		<script src="\${resourceUrl}/js/src/app/header/HeaderController.js"></script>
		<script src="\${resourceUrl}/js/src/app/header/LoginModalController.js"></script>
		<script src="\${resourceUrl}/js/src/app/collection/CollectionController.js"></script>
		<script src="\${resourceUrl}/js/src/app/index/BodyController.js"></script>
		<script src="\${resourceUrl}/js/src/app/search/SearchCriteriaController.js"></script>
		<script src="\${resourceUrl}/js/src/app/search/RecommandRestaurantController.js"></script>
		<script src="\${resourceUrl}/js/src/app/global/BrokerService.js"></script>
		<script src="\${resourceUrl}/js/src/app/global/HttpPromiseSerivce.js"></script>
		<script src="\${resourceUrl}/js/src/app/global/RestaurantService.js"></script>
		<script src="\${resourceUrl}/js/src/app/global/UtilService.js"></script>
		<script src="\${resourceUrl}/js/src/app/global/SearchCriteria.js"></script>
		<script src="\${resourceUrl}/js/src/app/global/UserService.js"></script>
		<script src="\${resourceUrl}/js/src/app/global/ReviewService.js"></script>
		<script src="\${resourceUrl}/js/src/app/global/SearchCriteriaService.js"></script>
		<script src="\${resourceUrl}/js/src/app/app.js"></script>`,
	"release": `<script src="\${resourceUrl}/js/src/app/app.js"></script>`
};


gulp.task("copyJsFiles", function() {
  gulp.src(JS_FILES).pipe(gulp.dest(JS_DIR));
});

gulp.task("scss", function() {
	gulp.src("scss/zomato.scss")
	.pipe(sass({includePaths: ['./scss/']}).on('error', sass.logError))
	.pipe(gulp.dest(CSS_DIR));
});

gulp.task("html", function() {
  gulp.src(HTML_FILES).pipe(gulp.dest(HTML_DIR));
});

gulp.task("inject-dev", function() {
    gulp.src("./app.jsp")
	.pipe(replace(/<!--javascript dependencies injection-->/, dependency['dev']))
	.pipe(gulp.dest("../../webapp/views/"));
});

gulp.task("inject-release", function() {
    gulp.src("./app.jsp")
	.pipe(replace(/<!--javascript dependencies injection-->/, dependency['release']))
	.pipe(gulp.dest("../../webapp/views/"));
});

gulp.task("watch", ["clean", "copyJsFiles", "scss", "html", "inject-dev"], function() {
		gulp.watch(JS_FILES, ["copyJsFiles"]);
		gulp.watch(SCSS_FILES, ["scss"]);
		gulp.watch(HTML_FILES, ["html"]);
});

gulp.task("clean", function() {
	del.sync([JS_DIR, CSS_DIR, HTML_DIR] , {force: true});
});

gulp.task("release", ["clean", "scss", "html", "inject-release"], function() {
    gulp.src(JS_FILES)
    .pipe(concat("app.js"))
    .pipe(uglify())
    .pipe(gulp.dest("../../webapp/resources/js/src/app/"))
});
