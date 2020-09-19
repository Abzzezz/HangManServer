package ga.abzzezz.hangman.server;

import ga.abzzezz.hangman.server.packet.PacketManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerChannelHandler extends SimpleChannelInboundHandler<String> {

    private PacketManager packetManager;

    @Override
    public void handlerAdded(final ChannelHandlerContext ctx) throws Exception {
        this.packetManager = new PacketManager(ctx.channel());
        Logger.getAnonymousLogger().log(Level.INFO, "new client connected: " + ctx.channel().remoteAddress().toString());
    }

    @Override
    public void handlerRemoved(final ChannelHandlerContext ctx) throws Exception {
        Logger.getAnonymousLogger().log(Level.WARNING, "client disconnected:" + ctx.channel().remoteAddress().toString());
        super.handlerRemoved(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close().sync();
        super.exceptionCaught(ctx, cause);
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext channelHandlerContext, final String readLine) throws Exception {
        packetManager.handlePacket(readLine);
    }
}


