
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.gdata.client.photos.PicasawebService
import com.google.gdata.data.photos.GphotoFeed
import com.google.gdata.data.photos.UserFeed
import java.io.File
import java.net.URL
import java.util.logging.ConsoleHandler
import java.util.logging.Level
import java.util.logging.Logger



private val API_PREFIX = "https://picasaweb.google.com/data/feed/api/user/"
private val JSON_FACTORY: JsonFactory = GsonFactory()
private val SCOPE_READ_PHOTOS = "https://picasaweb.google.com/data"
private val DATA_STORE_FACTORY = FileDataStoreFactory(File("/Users/crummy/.credentials/googleapi"))

fun main(args: Array<String>) {
    val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
    val credential = authorize(httpTransport)
    val locationQuery = LocationQuery("malcolmcrum-photolocation-0.1", credential)
    //locationQuery.printEmail()
    locationQuery.printAlbums()
}

private fun authorize(httpTransport: NetHttpTransport): Credential {
    // set up authorization code flow
    val clientId = "885382638582-hd41itsthergpemq3iorqdrkfi59tia4.apps.googleusercontent.com"
    val clientSecret = "5RW6GzkG8j9UXdjWYLurJCD4"
    val flow = GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientId, clientSecret, listOf(SCOPE_READ_PHOTOS, "https://www.googleapis.com/auth/userinfo.email"))
            .setDataStoreFactory(DATA_STORE_FACTORY)
            .build()
    return AuthorizationCodeInstalledApp(flow, LocalServerReceiver()).authorize("crummynz@gmail.com")
}

class LocationQuery(identifier: String, credential: Credential) {
    val service: PicasawebService = PicasawebService(identifier)

    init {
        val httpLogger = Logger.getLogger("com.google.gdata.client.http.HttpGDataRequest")
        httpLogger.level = Level.ALL
        val logHandler = ConsoleHandler()
        logHandler.level = Level.ALL
        httpLogger.addHandler(logHandler)
        service.setAuthSubToken(credential.accessToken)
    }

    fun printAlbums() {
        val feed = getFeed(API_PREFIX + "default?kind=album", UserFeed::class.java)
        println(feed.entries)
        feed.albumEntries.forEach({album -> println(album)})
    }

    private fun <T : GphotoFeed<*>> getFeed(feedHref: String, feedClass: Class<T>): T {
        println("Get Feed URL: " + feedHref)
        return service.getFeed(URL(feedHref), feedClass)
    }

    fun printEmail() {
        service.getFeed(URL("https://www.googleapis.com/userinfo/v2/me"), UserFeed::class.java)
    }
}