import io.github.stream29.githubinsight.spider.EntityProcessor
import io.github.stream29.githubinsight.spider.GithubApiProvider
import io.github.stream29.githubinsight.spider.Organization
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Test

class EntityTest {
    @Test
    fun processTest() {
        val githubApiProvider = GithubApiProvider(
            httpClient = httpClient,
            authToken = System.getenv("GITHUB_PUBLIC_TOKEN")
        )
        val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }
        val responseCollection = runBlocking {
            githubApiProvider.fetchBase(System.getenv("GITHUB_ACTOR"))
        }
        val entityProcessor = EntityProcessor()

        val user = runBlocking {
            entityProcessor.toUser(responseCollection)
        }
        println(json.encodeToString(user))

        if (responseCollection.orgsResponse.isNotEmpty()) {
            val org0 = runBlocking {
                entityProcessor.toOrganization(
                    responseCollection.orgsResponse[0],
                    githubApiProvider.fetchOrgMembers(responseCollection.orgsResponse[0].membersUrl)
                )
            }
            println(json.encodeToString(Organization.serializer(), org0))
        }

        if (responseCollection.reposResponse.isNotEmpty()) {
            val repo0 = runBlocking {
                entityProcessor.toRepository(
                    responseCollection.reposResponse[0],
                    githubApiProvider.fetchForks(responseCollection.reposResponse[0].forksUrl),
                    githubApiProvider.fetchContributors(responseCollection.reposResponse[0].contributorsUrl),
                    githubApiProvider.fetchStargazers(responseCollection.reposResponse[0].stargazersUrl),
                    githubApiProvider.fetchLanguages(responseCollection.reposResponse[0].languagesUrl),
                )
            }
            println(json.encodeToString(repo0))
        }
    }
}