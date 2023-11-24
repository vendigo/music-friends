package com.github.vendigo.musicfriends.utils;

import com.github.vendigo.musicfriends.model.PathNode;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import one.util.streamex.StreamEx;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.github.vendigo.musicfriends.utils.Utils.LINE_BREAK;

public class ResponseBuilder {

    private static final int FIRST_ELEMENT_INDEX = 0;
    private static final int SECOND_ELEMENT_INDEX = 1;
    private static final String NOTE_EMOJI = "\uD83C\uDFB5";
    private static final String BULLET_POINT_EMOJI = "\uD83D\uDD39";
    private static final String INDENT_4_SPACES = "    ";

    public static String buildPathResponse(List<PathNode> path, String footer) {
        List<String> rawLines = Lists.partition(path, 2).stream()
                .map(ResponseBuilder::pathNodeToString)
                .toList();

        List<String> lines = IntStream.range(0, rawLines.size())
                .mapToObj(i -> new IndexedLine(i, rawLines.get(i)))
                .map(indexedLine -> prependLine(indexedLine, rawLines.size()))
                .toList();

        List<String> linesWithFooter = Optional.ofNullable(footer)
                .map(f -> addElement(lines, LINE_BREAK + f))
                .orElse(lines);

        return String.join(LINE_BREAK, linesWithFooter);
    }

    private static String prependLine(IndexedLine indexedLine, int linesSize) {
        if (indexedLine.index() == 0 || indexedLine.index() == linesSize - 1) {
            return prependWith(indexedLine.line(), NOTE_EMOJI);
        } else {
            return prependWith(indexedLine.line(), INDENT_4_SPACES, BULLET_POINT_EMOJI);
        }
    }

    private static List<String> addElement(List<String> list, String element) {
        return ImmutableList.<String>builder()
                .addAll(list)
                .add(element)
                .build();
    }

    private static String prependWith(String line, String... prefixes) {
        return StreamEx.of(prefixes)
                .append(line)
                .joining();
    }

    private static String pathNodeToString(List<PathNode> nodes) {
        PathNode artistNode = nodes.get(FIRST_ELEMENT_INDEX);
        String str = "<b><a href='%s'>%s</a></b>"
                .formatted(artistNode.link(), artistNode.name());

        if (nodes.size() == 2) {
            PathNode trackNode = nodes.get(SECOND_ELEMENT_INDEX);
            str += " (<a href='%s'>%s</a>)"
                    .formatted(trackNode.link(), trackNode.name());
        }

        return str;
    }

    record IndexedLine(int index, String line) {
    }
}
