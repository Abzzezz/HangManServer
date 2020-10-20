package ga.abzzezz.hangman.server;

import ga.abzzezz.hangman.Main;
import ga.abzzezz.hangman.rooms.Player;
import ga.abzzezz.hangman.rooms.Room;
import ga.abzzezz.hangman.server.packet.PacketManager;
import ga.abzzezz.hangman.util.QuickLog;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerChannelHandler extends SimpleChannelInboundHandler<String> {

    private PacketManager packetManager;

    @Override
    public void handlerAdded(final ChannelHandlerContext ctx) {
        this.packetManager = new PacketManager(ctx.channel());
        QuickLog.log("new client connected", QuickLog.LogType.INFO);
    }

    @Override
    public void handlerRemoved(final ChannelHandlerContext ctx) throws Exception {
        removePlayer(ctx.channel());
        ctx.close().sync();
        QuickLog.log("client disconnected, closing socket", QuickLog.LogType.INFO);
        super.handlerRemoved(ctx);
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
        removePlayer(ctx.channel());
        ctx.close().sync();
        QuickLog.log("client exception caught, closing socket", QuickLog.LogType.WARNING);
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext channelHandlerContext, final String readLine) {
        packetManager.handlePacket(readLine);
    }

    private void removePlayer(final Channel playersChannel) {
        loop:
        for (final Room room : Main.ROOM_MANAGER.getRooms()) {
            for (final Player player : room.getPlayers()) {
                if (player.getPacketManager().getChannel().equals(playersChannel)) {
                    room.removePlayer(player);
                    break loop;
                }
            }
        }
    }
}


