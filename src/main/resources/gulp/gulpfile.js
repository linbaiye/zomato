var gulp = require('gulp');
var uglify = require('gulp-uglify');
var watch = require('gulp-watch');
var sass = require('gulp-sass');
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

gulp.task("watch", ["clean", "copyJsFiles", "scss", "html"], function() {
		gulp.watch(JS_FILES, ["copyJsFiles"]);
		gulp.watch(SCSS_FILES, ["scss"]);
		gulp.watch(HTML_FILES, ["html"]);
});

gulp.task("clean", function() {
	del.sync([JS_DIR] , {force: true});
});
