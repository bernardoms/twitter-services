#!/bin/bash
set -x
echo "runing"
awslocal sqs create-queue --queue-name summarize
set +x
