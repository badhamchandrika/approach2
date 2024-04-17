package com.aem.airmiles.web.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import org.junit.jupiter.api.BeforeEach;

public class TestUtil {

    public final AemContext aemctx= new AemContext(); //do not define this as static
    public static LogoModel logo;
    public static TopNav topNav;
    public static FooterNav footerNav;
    public static QuickLinksModel quickLink;
    public static SearchModel search;
    public static GetAppModel getAppModel;
    public static LinkModel linkModel;
    public static HeroSectionModel heroSectionModel;
    public static GetMoreMilesModel getMoreMiles;
    public static FooterHelpsModel footerHelps;
    public static TextImageModel textImageModel;
    public static OptinModel optin;
    public static UseMilesSection useMiles;
    public static ShellHeroModel shellHero;
    public static ContentBlockShell contentBlockShell;
    public static PrizesShellModel prizesShellModel;
    public static OffersContentFragmentModel offersContentFragmentModel;

    @BeforeEach
    public void setUp() //did not make this method static
    {
        aemctx.addModelsForClasses(LogoModel.class);
        aemctx.addModelsForClasses(TopNav.class);
        aemctx.addModelsForClasses(QuickLinksModel.class);
        aemctx.addModelsForClasses(SearchModel.class);
        aemctx.addModelsForClasses(GetAppModel.class);
        aemctx.addModelsForClasses(LinkModel.class);
        aemctx.addModelsForClasses(HeroSectionModel.class);
        aemctx.addModelsForClasses(GetMoreMilesModel.class);
        aemctx.addModelsForClasses(FooterHelpsModel.class);
        aemctx.addModelsForClasses(TextImageModel.class);
        aemctx.addModelsForClasses(OptinModel.class);
        aemctx.addModelsForClasses(UseMilesSection.class);
        aemctx.addModelsForClasses(ShellHeroModel.class);
        aemctx.addModelsForClasses(ContentBlockShell.class);
        aemctx.addModelsForClasses(PrizesShellModel.class);
        aemctx.addModelsForClasses(OffersContentFragmentModel.class);
        
        aemctx.load().json("/com.aem.airmiles.web.core/LogoModelTest.json", "/logomodelcontent");
        aemctx.load().json("/com.aem.airmiles.web.core/TopNavModelTest.json", "/topnavmodelcontent");
        aemctx.load().json("/com.aem.airmiles.web.core/QuickLinksModelTest.json", "/quicklinksmodelcontent");
        aemctx.load().json("/com.aem.airmiles.web.core/SearchModelTest.json", "/searchmodelcontent");
        aemctx.load().json("/com.aem.airmiles.web.core/GetAppModelTest.json", "/getAppModelcontent");
        aemctx.load().json("/com.aem.airmiles.web.core/LinkModelTest.json", "/linkmodelcontent");
        aemctx.load().json("/com.aem.airmiles.web.core/HeroSectionModelTest.json", "/herosectionmodelcontent");
        aemctx.load().json("/com.aem.airmiles.web.core/GetMoreMilesModelTest.json", "/getmoremilesmodelcontent");
        aemctx.load().json("/com.aem.airmiles.web.core/FooterHelpsModelTest.json", "/footerhelpsmodelcontent");
        aemctx.load().json("/com.aem.airmiles.web.core/OptinModelTest.json", "/optinmodelcontent");
        aemctx.load().json("/com.aem.airmiles.web.core/UseMilesSectionTest.json", "/usemilesmodelcontent");
        aemctx.load().json("/com.aem.airmiles.web.core/ContentBlockShellTest.json", "/contentblockshell");
        aemctx.load().json("/com.aem.airmiles.web.core/PrizesShellModelTest.json", "/prizesshellmodel");
        aemctx.load().json("/com.aem.airmiles.web.core/OffersCarouselModelTest.json", "/offerscarouselsmodelcontent");

    }
}
