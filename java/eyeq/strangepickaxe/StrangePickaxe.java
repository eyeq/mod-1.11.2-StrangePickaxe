package eyeq.strangepickaxe;

import eyeq.strangepickaxe.event.StrangePickaxeEventHandler;
import eyeq.util.client.model.UModelCreator;
import eyeq.util.client.model.UModelLoader;
import eyeq.util.client.model.gson.ItemmodelJsonFactory;
import eyeq.util.client.renderer.ResourceLocationFactory;
import eyeq.util.client.resource.ULanguageCreator;
import eyeq.util.client.resource.lang.LanguageResourceManager;
import eyeq.util.item.crafting.UCraftingManager;
import eyeq.util.oredict.UOreDictionary;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;

import static eyeq.strangepickaxe.StrangePickaxe.MOD_ID;

@Mod(modid = MOD_ID, version = "1.0", dependencies = "after:eyeq_util")
@Mod.EventBusSubscriber
public class StrangePickaxe {
    public static final String MOD_ID = "eyeq_strangepickaxe";

    @Mod.Instance(MOD_ID)
    public static StrangePickaxe instance;
    private static final ResourceLocationFactory resource = new ResourceLocationFactory(MOD_ID);

    public static Item strangePickaxe;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new StrangePickaxeEventHandler());
        addRecipes();
        if(event.getSide().isServer()) {
            return;
        }
        renderItemModels();
        createFiles();
    }

    @SubscribeEvent
    protected static void registerItems(RegistryEvent.Register<Item> event) {
        strangePickaxe = new ItemPickaxe(Item.ToolMaterial.STONE){}.setUnlocalizedName("strangePickaxe");

        GameRegistry.register(strangePickaxe, resource.createResourceLocation("strange_pickaxe"));
    }

    public static void addRecipes() {
        GameRegistry.addRecipe(UCraftingManager.getRecipePickaxe(new ItemStack(strangePickaxe),
                UOreDictionary.OREDICT_COBBLESTONE, UOreDictionary.OREDICT_COBBLESTONE));
    }

    @SideOnly(Side.CLIENT)
    public static void renderItemModels() {
        UModelLoader.setCustomModelResourceLocation(strangePickaxe);
    }

    public static void createFiles() {
        File project = new File("../1.11.2-StrangePickaxe");

        LanguageResourceManager language = new LanguageResourceManager();

        language.register(LanguageResourceManager.EN_US, strangePickaxe, "Strange Pickaxe");
        language.register(LanguageResourceManager.JA_JP, strangePickaxe, "奇妙なツルハシ");

        ULanguageCreator.createLanguage(project, MOD_ID, language);

        UModelCreator.createItemJson(project, strangePickaxe, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
    }
}
