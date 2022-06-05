#!/bin/bash
find . -name '.DS_Store' -type f -delete
find . -name '*.iml' -type f -delete
find . -name '.classpath' -type f -delete
find . -name '.project' -type f -delete
find . -type d -name '.factorypath' | xargs rm -r
find . -type d -name '.asciidoctor' | xargs rm -r
find . -type d -name '.idea' | xargs rm -r
find . -type d -name '.settings' | xargs rm -r
find . -type d -name 'target' | xargs rm -r
