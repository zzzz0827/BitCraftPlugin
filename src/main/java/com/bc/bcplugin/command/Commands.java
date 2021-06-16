package com.bc.bcplugin.command;

import com.bc.bcplugin.GUI.OpenCoinAboutGUIEvent;
import com.bc.bcplugin.GUI.OpenCoinListGUIEvent;
import com.bc.bcplugin.GUI.OpenCoinMarketGUIEvent;
import com.bc.bcplugin.Plugin;
import com.bc.bcplugin.command.cmds.MoneyCheckCommand;
import com.bc.bcplugin.command.cmds.MoneyGiveCommand;
import com.bc.bcplugin.command.cmds.MoneyReduceCommand;
import com.bc.bcplugin.command.cmds.MoneySendCommand;
import com.bc.bcplugin.utils.Messager;
import oracle.net.ns.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * 명령어 실행문 코드입니다.
 */
public class Commands implements CommandExecutor, TabCompleter {

    private final Plugin plugin;
    private final CommandConfig mainCommandConfig;

    public CommandConfig getCommand() {
        return mainCommandConfig;
    }

    public Commands(Plugin plugin) {
        this.plugin = plugin;
        this.mainCommandConfig = new CommandConfig() {
            @Override
            protected boolean onCommand(CommandSender sender, String command, String[] args) {
                Player player = (Player) sender;

                // 서브 커맨드가 입력되지 않았을 경우
                if(args.length == 0) {
                    player.sendMessage("§7===================§a[ §eBitCraft §a]§7===================");
                    player.sendMessage("§eMade By : §a임동우");
                    player.sendMessage("§eEmail : §azzzz0827@naver.com");
                    player.sendMessage("§ePlugin Version : §a1.0");
                    player.sendMessage("§e/bc help로 §a사용 할 수 있는 §c명령어§a를 확인하세요!");
                    player.sendMessage("§e/bc help §cadmin§e으로 §c관리자가 §a사용 할 수 있는 §c명령어§a를 확인하세요!");
                    player.sendMessage("§7===================§a[ §eBitCraft §a]§7===================");

                    return true;
                }
                return false;
            }
        };

        // 서브 커맨드를 추가하는 명령어입니다.
        mainCommandConfig.addSubCommand("help", new CommandConfig() {
            @Override
            protected boolean onCommand(CommandSender sender, String command, String[] args) {
                Player player = (Player) sender;
                player.sendMessage("§7===================§a[ §eBitCraft §a]§7===================");
                player.sendMessage("§e/bc §aabout : §b(비트코인)의 현재 정보를 확인할 수 있는 GUI를 출력합니다..");
                player.sendMessage("§e/bc §amarket : §b비트코인을 구매/판매 할 수 있는 시장 GUI를 출력합니다.");
                player.sendMessage("§e/bc §alist : §b비트코인의 종류를 보여주는 GUI를 출력합니다.");
                player.sendMessage("§e/bc §amoney : §b보유 자산을 관리하는 명령어들 출력합니다.");
                player.sendMessage("§7===================§a[ §eBitCraft §a]§7===================");
                return true;
            }
        });
        mainCommandConfig.addSubCommand("list", new CommandConfig() {
            @Override
            protected boolean onCommand(CommandSender sender, String command, String[] args) {
                OpenCoinListGUIEvent openGUI = new OpenCoinListGUIEvent((Player) sender);
                Bukkit.getPluginManager().callEvent(openGUI);
                return true;
            }
        });
        mainCommandConfig.addSubCommand("about", new CommandConfig() {
            @Override
            protected boolean onCommand(CommandSender sender, String command, String[] args) {
                OpenCoinAboutGUIEvent openGUI = new OpenCoinAboutGUIEvent((Player) sender);
                Bukkit.getPluginManager().callEvent(openGUI);
                return true;
            }
        });
        mainCommandConfig.addSubCommand("market", new CommandConfig() {
            @Override
            protected boolean onCommand(CommandSender sender, String command, String[] args) {
                OpenCoinMarketGUIEvent openGUI = new OpenCoinMarketGUIEvent((Player) sender);
                Bukkit.getPluginManager().callEvent(openGUI);
                return true;
            }
        });

        final CommandConfig moneyCommand = new CommandConfig() {
            {
                addSubCommand("check", new CommandConfig() {
                    @Override
                    protected boolean onCommand(CommandSender sender, String command, String[] args) {
                        new MoneyCheckCommand(sender);
                        return true;
                    }
                });

                addSubCommand("give", new CommandConfig() {
                    @Override
                    protected boolean onCommand(CommandSender sender, String command, String[] args) {
                        Player player = (Player) sender;
                        if(args.length == 0) Messager.sendErrorMessage(player, "플레이어를 입력해 주세요!");
                        else if (args.length == 1) Messager.sendErrorMessage(player, "금액을 입력해 주세요!");
                        else {
                            new MoneyGiveCommand(player, args[0], args[1]);
                        }
                        return true;
                    }
                });

                addSubCommand("reduce", new CommandConfig() {
                    @Override
                    protected boolean onCommand(CommandSender sender, String command, String[] args) {
                        Player player = (Player) sender;
                        if(args.length == 0) Messager.sendErrorMessage(player, "플레이어를 입력해 주세요!");
                        else if (args.length == 1) Messager.sendErrorMessage(player, "금액을 입력해 주세요!");
                        else {
                            new MoneyReduceCommand(player, args[0], args[1]);
                        }
                        return true;
                    }
                });

                addSubCommand("send", new CommandConfig() {
                    @Override
                    protected boolean onCommand(CommandSender sender, String command, String[] args) {
                        Player player = (Player) sender;
                        if(args.length == 0) Messager.sendErrorMessage(player, "플레이어를 입력해 주세요!");
                        else if (args.length == 1) Messager.sendErrorMessage(player, "금액을 입력해 주세요!");
                        else {
                            new MoneySendCommand(player, args[0], args[1]);
                        }
                        return true;
                    }
                });
            }

            @Override
            protected boolean onCommand (CommandSender sender, String command, String[]args){

                Player player = (Player) sender;
                player.sendMessage("§7===================§a[ §eBitCraft §a]§7===================");
                player.sendMessage("§e/bc §amoney §ccheck : §b현재 자신의 보유 자산을 출력합니다.");
                player.sendMessage("§e/bc §amoney §csend [플레이어] [금액] : §b[플레이어]에게 [금액]만큼의 돈을 이체합니다.");
                player.sendMessage("§e/bc §amoney §cgive [플레이어] [금액] : §b[플레이어]에게 [금액]만큼 돈을 지급합니다. §c(Admin Only).");
                player.sendMessage("§e/bc §amoney §creduce [플레이어] [금액] : §b[플레이어]의 돈을 [금액]만큼 차감합니다. §c(Admin Only).");
                player.sendMessage("§7===================§a[ §eBitCraft §a]§7===================");

                return true;
            }

        };
        mainCommandConfig.addSubCommand("money", moneyCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        mainCommandConfig.handleCommand(sender, label, args);

        if(args.length == 0) return false;

        return true;
    }

    /**
     * 다중 명령어 (EX. /command subcmd1 subcmd2)를 제작하는 메소드입니다.
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("bc") || label.equalsIgnoreCase("bit") ||
            label.equalsIgnoreCase("coin") || label.equalsIgnoreCase("bitcoin")) {
            switch (args.length) {
                case 1:
                    List<String> subCommands = Messager.asList("list", "about", "market", "money");
                    if(args[0].isEmpty()) {
                        return subCommands;
                    }else {
                        subCommands.removeIf(cmd -> !cmd.toLowerCase().startsWith(args[0].toLowerCase()));
                        return subCommands;
                    }
                case 2:
                    if(args[0].equalsIgnoreCase("money")) {
                        List<String> getLists = Messager.asList("check", "give", "reduce", "send");
                        if(args[1].isEmpty()) {
                            return getLists;
                        }else {
                            getLists.removeIf(getList -> !getList.toLowerCase().startsWith(args[1].toLowerCase()));
                            return getLists;
                        }
                    }
                case 4:
                    return Messager.asList((String) null);
            }
        }
        return null;
    }
}