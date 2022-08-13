package com.github.vendigo.musicfriends.bot;

import com.github.vendigo.musicfriends.command.CommandParser;
import com.github.vendigo.musicfriends.command.CommandType;
import com.github.vendigo.musicfriends.command.FindArtistCommand;
import com.github.vendigo.musicfriends.model.ArtistNode;
import com.github.vendigo.musicfriends.service.ArtistService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@AllArgsConstructor
public class InlineQueryHandler {

    private final ArtistService artistService;

    public AnswerInlineQuery handleQuery(InlineQuery inlineQuery) {

        String query = inlineQuery.getQuery();
        List<InlineQueryResultArticle> artists = processQuery(query);

        return AnswerInlineQuery.builder()
                .inlineQueryId(inlineQuery.getId())
                .results(artists)
                .cacheTime(0)
                .build();
    }

    private List<InlineQueryResultArticle> processQuery(String message) {
        Optional<FindArtistCommand> commandOptional = CommandParser.parseFindArtistCommand(message);

        if (commandOptional.isEmpty()) {
            log.info("Skipping query: {}", message);
            return List.of();
        }

        FindArtistCommand command = commandOptional.get();

        log.info("Processing query: {}", command);
        return artistService.findArtist(command.query()).stream()
                .map(artistNode -> mapArtist(command.commandType(), artistNode))
                .toList();
    }

    private InlineQueryResultArticle mapArtist(CommandType commandType, ArtistNode artist) {
        String content = String.format("%s: %s\n%s%d", commandType.getValue(), artist.getName(), Utils.DEEZER_ARTIST_LINK, artist.getId());
        InputTextMessageContent inputMessageContent = new InputTextMessageContent(content);
        inputMessageContent.setParseMode("html");

        return InlineQueryResultArticle.builder()
                .id(artist.getId().toString())
                .title(artist.getName())
                .inputMessageContent(inputMessageContent)
                .thumbUrl(artist.getPicture())
                .build();

    }
}
