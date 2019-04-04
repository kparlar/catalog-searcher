#!/bin/bash

set -e
echo  "starting load-test on `date`"
now=`date +%Y-%m-%d.%H:%M:%S`

while ! curl catalog-searcher:8011;
do
 echo sleeping;
 sleep 1;
done;
echo Connected!;

jmeter -n -t /catalog-searcher-test.jmx \
      -j /output/catalog-searcher-$now.log \
      -l /output/catalog-searcher-results-$now.jtl \
      -Jjmeter.save.saveservice.output_format=xml \
      -Jjmeter.save.saveservice.response_data=true \
      -Jjmeter.save.saveservice.samplerData=true \
      -JnumberOfThreads=1

echo "load-test finished on `date`"
