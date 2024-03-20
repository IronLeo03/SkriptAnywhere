package me.ironleo03.skriptanywhere.elements;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import me.ironleo03.skriptanywhere.network.client.AnywhereSocket;
import me.ironleo03.skriptanywhere.network.server.AnywhereServerSocket;

public class ClassInfos {
    /*
    TODO there has to be a better way to do this
    https://docs.skunity.com/guides/guide/creating-a-type-classinfo.33
     */
    static {
        Classes.registerClass(
                new ClassInfo<>(AnywhereServerSocket.class, "anywhereserversocket")
                        .user("anywhereserversocket")
                        .name("Server Socket")
                        .defaultExpression(new EventValueExpression<>(AnywhereServerSocket.class))
                        .parser(new Parser<AnywhereServerSocket>() {
                            @Override
                            public boolean canParse(ParseContext context) {
                                return false;
                            }

                            @Override
                            public String toString(AnywhereServerSocket o, int flags) {
                                return "server";
                            }

                            @Override
                            public String toVariableNameString(AnywhereServerSocket o) {
                                return null;
                            }
                        }));
        Classes.registerClass(
                new ClassInfo<>(AnywhereSocket.class, "anywheresocket")
                        .user("anywheresocket")
                        .name("Socket")
                        .defaultExpression(new EventValueExpression<>(AnywhereSocket.class))
                        .parser(new Parser<AnywhereSocket>() {
                            @Override
                            public boolean canParse(ParseContext context) {
                                return false;
                            }

                            @Override
                            public String toString(AnywhereSocket o, int flags) {
                                return "socket";
                            }

                            @Override
                            public String toVariableNameString(AnywhereSocket o) {
                                return null;
                            }
                        }));
    }
}
