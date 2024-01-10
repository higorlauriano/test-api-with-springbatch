This application is the result of my study on SpringBatch.

Runs with Java 17, with embedded H2 Database;

Upon startup a job `setupDatabase` is triggered, which:

1. Reads content from a CSV file that looks like this:
  ```
  year;title;studios;producers;winner
  1980;Can't Stop the Music;Associated Film Distribution;Allan Carr;yes
  ```
2. Persists each separate entity, `Producer`, `Studio`, `Movie` in a unique manner (based by name)

The persisted data is then available for querying or editing via level 2 of maturity REST API.
Endpoints are based on entities names:
`/producer`,
`/studio`,
`/movie`
