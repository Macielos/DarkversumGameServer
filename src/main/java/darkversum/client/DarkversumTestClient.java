package darkversum.client;

import darkversum.dto.PlayerInMatchDto;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.core.util.IOUtils;

import java.io.IOException;
import java.io.InputStreamReader;

public class DarkversumTestClient {

    private static final String URL = "http://localhost:8090";

    private static CloseableHttpClient client;

    public static void main(String[] args) throws Exception {
        CredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("user", "dupa"));
        client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build();

        findMatch(new PlayerInMatchDto("Macielos", "OWDI"));

        // insertPlayer(new PlayerDto(null, "Macielos", "Poland", Player.Status.WAITING));
      //  insertPlayer(new PlayerDto(null, "Nyx", "Poland", Player.Status.WAITING));

   //     MatchDto match = findMatch(new PlayerInMatchDto("Macielos", "OWDI"));
    //    match = findMatch(new PlayerInMatchDto("Nyx", "VH"));

    }
//
//    private static void insertPlayer(PlayerDto playerDto) {
//        Mono<PlayerDto> playerDtoMono = Mono.just(playerDto);
//        System.out.println("player: "+playerDtoMono.block());
//
//        Mono<PlayerDto> playerDtoMonoFromPost = client.post()
//                .uri("/player")
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(playerDtoMono, PlayerDto.class)
//                .retrieve()
//                .bodyToMono(PlayerDto.class);
//        System.out.println("playerFromPost: "+playerDtoMonoFromPost.block());
//    }
//

    private static void findMatch(PlayerInMatchDto playerInMatchDto) throws IOException {
        try(CloseableHttpResponse response = client.execute(new HttpGet(
                String.format(URL + "/match/findMatch?player=%s&faction=%s&players=%d",
                        playerInMatchDto.getPlayerName(),
                        playerInMatchDto.getFactionName(),
                        2)))) {
            String responseBody = IOUtils.toString(new InputStreamReader(response.getEntity().getContent()));
            System.out.println("Match from get: "+responseBody);
        }
    }
//
//    private static MatchDto startMatch(List<PlayerInMatchDto> playersInGame) {
//        Mono<MatchDto> matchDtoMono = Mono.just(new MatchDto(null, MatchDto.Status.READY, playersInGame));
//        System.out.println("match: "+matchDtoMono.block());
//
//        Mono<MatchDto> matchDtoMonoFromPost = client.post()
//                .uri("/match")
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(matchDtoMono, MatchDto.class)
//                .retrieve()
//                .bodyToMono(MatchDto.class);
//        MatchDto matchDtoFromPost = matchDtoMonoFromPost.block();
//        System.out.println(matchDtoFromPost);
//        return matchDtoFromPost;
//    }
}
