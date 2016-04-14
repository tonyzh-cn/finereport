#! /bin/bash
svn up designer
svn up designer_base
svn up designer_chart
svn up designer_form
curl -o lib/fr-core-8.0.jar http://download.finedevelop.com/fr-core-8.0.jar
curl -o lib/fr-chart-8.0.jar http://download.finedevelop.com/fr-chart-8.0.jar
curl -o lib/fr-report-8.0.jar http://download.finedevelop.com/fr-report-8.0.jar
curl -o lib/fr-platform-8.0.jar http://download.finedevelop.com/fr-platform-8.0.jar
curl -o lib/fr-performance-8.0.jar  http://download.finedevelop.com/fr-performance-8.0.jar 