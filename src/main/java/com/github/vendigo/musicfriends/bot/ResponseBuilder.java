package com.github.vendigo.musicfriends.bot;

import com.github.vendigo.musicfriends.model.PathNode;
import com.github.vendigo.musicfriends.utils.Utils;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResponseBuilder {

    public String buildPathResponse(List<PathNode> path) {
        List<String> lines = Lists.partition(path, 2).stream()
                .map(ResponseBuilder::pathNodeToString)
                .collect(Collectors.toList());
        for (int i = 0; i < lines.size(); i++) {
            if (i == 0 || i == lines.size() - 1) {
                lines.set(i, "\uD83C\uDFB5" + lines.get(i));
            } else {
                lines.set(i, "    \uD83D\uDD39" + lines.get(i));
            }
        }

        return String.join("\n", lines);
    }

    private static String pathNodeToString(List<PathNode> nodes) {
        PathNode artistNode = nodes.get(0);
        StringBuilder str = new StringBuilder()
                .append("<b><a href='")
                .append(artistNode.link())
                .append("'>")
                .append(artistNode.name())
                .append("</a></b>");

        if (nodes.size() == 2) {
            PathNode trackNode = nodes.get(1);
            str.append(" (<a href='")
                    .append(trackNode.link())
                    .append("'>")
                    .append(trackNode.name())
                    .append("</a>)");
        }

        return str.toString();
    }
}
