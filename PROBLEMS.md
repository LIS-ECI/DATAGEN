## PROBLEMS TO BE ADDRESSED



#### 8/9/2017

The application is almost ready to work:

- Configuration for sorting strategy was temporarily hardcoded. Original configuration loading mechanism doesn't work well with Maven.
- Implicit dependency to "jsc/distributions/Normal" not solved at runtime (local jar is included, as it is not available in a Maven repository.

