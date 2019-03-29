package darkversum.client;

import darkversum.dto.MatchDto;
import darkversum.dto.PlayerDto;
import darkversum.dto.PlayerInMatchDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DarkversumTestWebClient {

    private static final String URL = "http://localhost:8090";

    private static WebClient client = WebClient.builder()
            .baseUrl(URL)

            .filter(ExchangeFilterFunctions.basicAuthentication("user", "dupa"))
            .build();

    public static void main(String[] args) throws Exception {
       // insertPlayer(new PlayerDto(null, "Macielos", "Poland", Player.Status.WAITING));
      //  insertPlayer(new PlayerDto(null, "Nyx", "Poland", Player.Status.WAITING));

        MatchDto match = findMatch(new PlayerInMatchDto("Macielos", "OWDI"));
        match = findMatch(new PlayerInMatchDto("Nyx", "VH"));

    }

    private static void insertPlayer(PlayerDto playerDto) {
        Mono<PlayerDto> playerDtoMono = Mono.just(playerDto);
        System.out.println("player: "+playerDtoMono.block());

        Mono<PlayerDto> playerDtoMonoFromPost = client.post()
                .uri("/player")
                .contentType(MediaType.APPLICATION_JSON)
                .body(playerDtoMono, PlayerDto.class)
                .retrieve()
                .bodyToMono(PlayerDto.class);
        System.out.println("playerFromPost: "+playerDtoMonoFromPost.block());
    }

    private static MatchDto findMatch(PlayerInMatchDto playerInMatchDto) {
        Map<String, String> params = new HashMap<>();
        params.put("player", playerInMatchDto.getPlayerName());
        params.put("faction", playerInMatchDto.getFactionName());
        params.put("players", "2");
        MatchDto matchFromGet = client.get()
                .uri("/match/findMatch", params)
                .retrieve()
                .bodyToMono(MatchDto.class)
                .block();
        System.out.println("Match from get: "+matchFromGet);

        return matchFromGet;
    }

    private static MatchDto startMatch(List<PlayerInMatchDto> playersInGame) {
        Mono<MatchDto> matchDtoMono = Mono.just(new MatchDto(null, MatchDto.Status.READY, playersInGame, null, null));
        System.out.println("match: "+matchDtoMono.block());

        Mono<MatchDto> matchDtoMonoFromPost = client.post()
                .uri("/match")
                .contentType(MediaType.APPLICATION_JSON)
                .body(matchDtoMono, MatchDto.class)
                .retrieve()
                .bodyToMono(MatchDto.class);
        MatchDto matchDtoFromPost = matchDtoMonoFromPost.block();
        System.out.println(matchDtoFromPost);
        return matchDtoFromPost;
    }
}
