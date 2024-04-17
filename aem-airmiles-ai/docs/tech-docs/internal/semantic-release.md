# Semantic-release
Versioning is fully automated and managed by [semantic-release](https://github.com/semantic-release/semantic-release).

## How does it work?

### Commit message format

**semantic-release** uses the commit messages to determine the type of changes in the codebase. Following formalized conventions for commit messages, **semantic-release** automatically determines the next [semantic version](https://semver.org) number, generates a changelog and publishes the release.

By default **semantic-release** uses [Angular Commit Message Conventions](https://github.com/angular/angular.js/blob/master/DEVELOPERS.md#-git-commit-guidelines). The commit message format can be changed with the [`preset` or `config` options](docs/usage/configuration.md#options) of the [@semantic-release/commit-analyzer](https://github.com/semantic-release/commit-analyzer#options) and [@semantic-release/release-notes-generator](https://github.com/semantic-release/release-notes-generator#options) plugins.

Tools such as [commitizen](https://github.com/commitizen/cz-cli) or [commitlint](https://github.com/conventional-changelog/commitlint) can be used to help contributors and enforce valid commit messages.

Here is an example of the release type that will be done based on a commit messages:

| Commit message                                                                                                                                                                                   | Release type               |
| ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | -------------------------- |
| `fix(pencil): stop graphite breaking when too much pressure applied`                                                                                                                             | Patch Release              |
| `feat(pencil): add 'graphiteWidth' option`                                                                                                                                                       | ~~Minor~~ Feature Release  |
| `perf(pencil): remove graphiteWidth option`<br><br>`BREAKING CHANGE: The graphiteWidth option has been removed.`<br>`The default graphite width of 10mm is always used for performance reasons.` | ~~Major~~ Breaking Release |

# Tips
  
- Please make sure commit messages are properly formatted according to the semantic release standards mentioned above
- Windows Users: highly recommend installing gitbash to have a similar experience to Linux/ Mac Users
- See https://spring.io/guides/gs/spring-boot/#scratch for more information
- In order to be better stewards of this pipeline, please ensure that any modifications to your Jenkinsfiles involving adding 'input' steps (a.k.a. Approval Gates) do not consume any agents/executors in Jenkins. Failing to do this will add uneccessary congestion to Jenkins, and dramatically increase lead deployment time into production for other teams. 
  - [Jenkinsfile-cd.groovy](https://github.com/LoyaltyOne/starter-kit-springboot-api/blob/TD-2381-no-holding/jenkins/Jenkinsfile-cd.groovy) is already optimised to efficiently utilize Jenkins's resources and can serve as a good example. 

## Scorecard
[scorecard.md](scorecard.md)


# Enable / Disable Dependabot

- Please see following link for enable / disable dependabot
  https://docs.github.com/en/code-security/dependabot/dependabot-security-updates/configuring-dependabot-security-updates
