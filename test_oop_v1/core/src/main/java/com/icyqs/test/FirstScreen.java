package com.icyqs.test;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class FirstScreen implements Screen {

    SpriteBatch batch;
    OrthographicCamera camera;

    Texture bg;
    Texture idleSheet, runSheet;
    Animation<TextureRegion> idleAnimation;
    Animation<TextureRegion> runAnimation;

    float stateTime = 0;
    float x = 200, y = 200;
    float speed = 200;
    boolean facingRight = true;

    final float CAT_W = 48 / 2;
    final float CAT_H = 64 / 2;
    float mapW, mapH;
    final float VIEW_W = 400;
    final float VIEW_H = 225;

    Stage stage;
    BitmapFont font;

    Texture panelTex, btnTex, btnPressedTex;
    Texture iconHeart, iconCoin, iconPause;

    Table chatLog;
    ScrollPane scrollPane;
    Array<String> messages = new Array<>();
    boolean chatOpen = true;

    @Override
    public void show() {
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEW_W, VIEW_H);

        bg = new Texture(Gdx.files.internal("map/city.png"));
        mapW = bg.getWidth();
        mapH = bg.getHeight();
        idleSheet = new Texture(Gdx.files.internal("player/orange/IDLE.png"));
        runSheet = new Texture(Gdx.files.internal("player/orange/RUN.png"));

        int[] idleX = { 26, 106, 186, 266, 346, 426, 506, 586 };
        TextureRegion[] idleFrames = new TextureRegion[idleX.length];
        for (int i = 0; i < idleX.length; i++)
            idleFrames[i] = new TextureRegion(idleSheet, idleX[i], 0, 30, 64);

        int[] runX = { 19, 96, 176, 260, 339, 419, 498, 580 };
        int[] runW = { 45, 46, 48, 44, 44, 44, 46, 43 };
        TextureRegion[] runFrames = new TextureRegion[runX.length];
        for (int i = 0; i < runX.length; i++)
            runFrames[i] = new TextureRegion(runSheet, runX[i], 0, runW[i], 64);

        idleAnimation = new Animation<>(0.1f, idleFrames);
        runAnimation = new Animation<>(0.08f, runFrames);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);
        runAnimation.setPlayMode(Animation.PlayMode.LOOP);

        // Load TrueType font with Vietnamese Unicode support
        try {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 20;
            parameter.color = Color.WHITE;
            parameter.borderWidth = 1f;
            parameter.borderColor = Color.BLACK;
            font = generator.generateFont(parameter);
            generator.dispose();
        } catch (Exception e) {
            // Fallback to default if font not found
            font = new BitmapFont();
            font.getData().setScale(1.2f);
            font.setColor(Color.WHITE);
        }

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        panelTex = new Texture(Gdx.files.internal("ui/panel/panel_blue.png"));
        btnTex = new Texture(Gdx.files.internal("ui/button/button_blue.png"));
        btnPressedTex = new Texture(Gdx.files.internal("ui/button/button_blue_pressed.png"));
        iconHeart = new Texture(Gdx.files.internal("ui/icon/heart.png"));
        iconCoin = new Texture(Gdx.files.internal("ui/icon/coin.png"));
        iconPause = new Texture(Gdx.files.internal("ui/icon/pause.png"));

        buildChatUI();

        addMessage("╔════════════════════════════════════════╗");
        addMessage("║    BO GIAO DUC VA DAO TAO             ║");
        addMessage("║   TRUONG DH BACH KHOA HA NOI          ║");
        addMessage("║                                        ║");
        addMessage("║        HOC PHAN: LAP TRINH HUONG       ║");
        addMessage("║              DOI TUONG                ║");
        addMessage("║                                        ║");
        addMessage("║        MEOW DU AN: CAT LIFE MEOW       ║");
        addMessage("║          NHOM 5                        ║");
        addMessage("╠════════════════════════════════════════╣");
        addMessage("║ 202416867  │ Nguyen Binh              ║");
        addMessage("║ 202416855  │ Nguyen Quang Anh         ║");
        addMessage("║ 202417058  │ Bui Ngoc Trung           ║");
        addMessage("║ 202416842  │ Vu Lan Anh               ║");
        addMessage("║ 202417010  │ Hoang Binh Phuong        ║");
        addMessage("╚════════════════════════════════════════╝");
        addMessage("");
        addMessage("Meo: Xin chao! Toi la Meo trong tro choi nay!");
        addMessage("Huong dan: Dung phim mui ten de di chuyen");
    }

    private void buildChatUI() {
        int sw = Gdx.graphics.getWidth();
        int sh = Gdx.graphics.getHeight();

        float panelH = sh * 0.35f;
        float panelW = sw * 0.95f;
        float panelX = (sw - panelW) / 2f;
        float panelY = 8;
        NinePatch panelPatch = new NinePatch(panelTex, 4, 4, 4, 4);
        NinePatchDrawable panelDrawable = new NinePatchDrawable(panelPatch);

        Table container = new Table();
        container.setBounds(panelX, panelY, panelW, panelH);
        container.setBackground(panelDrawable);
        container.pad(8);

        Table topBar = new Table();

        topBar.add(makeIconButton(iconHeart, () -> addMessage("❤ HP: 100/100"))).size(32, 32).pad(2);
        topBar.add(makeIconButton(iconCoin, () -> addMessage("💰 Coins: 999"))).size(32, 32).pad(2);
        topBar.add().expandX();

        topBar.add(makeIconButton(iconPause, () -> addMessage("⏸ Game paused!"))).size(32, 32).pad(2);

        container.add(topBar).fillX().expandX().row();
        chatLog = new Table();
        chatLog.top().left();
        chatLog.pad(4);

        scrollPane = new ScrollPane(chatLog);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setFadeScrollBars(false);

        container.add(scrollPane).expand().fill().padTop(4).row();
        Table btnRow = new Table();

        btnRow.add(makeTextButton("Chào bạn!", () -> addMessage("Mèo: Chào! Vui lòng gặp bạn!"))).pad(3);
        btnRow.add(makeTextButton("Tôi đang ở đâu?",
                () -> addMessage("Mèo: Tôi đang ở vị trí (" + (int) x + ", " + (int) y + ")"))).pad(3);
        btnRow.add(makeTextButton("Tiếp tục...", () -> addMessage("Mèo: Cùng nhau phiêu lưu nào!"))).pad(3);

        container.add(btnRow).fillX().padTop(4);

        stage.addActor(container);
    }

    private ImageButton makeIconButton(Texture tex, Runnable onClick) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(new TextureRegion(tex));
        style.imageDown = new TextureRegionDrawable(new TextureRegion(tex));
        ImageButton btn = new ImageButton(style);
        btn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent e, float x, float y, int ptr, int btn2) {
                return true;
            }

            @Override
            public void touchUp(InputEvent e, float x, float y, int ptr, int btn2) {
                onClick.run();
            }
        });
        return btn;
    }

    private com.badlogic.gdx.scenes.scene2d.ui.Button makeTextButton(String text, Runnable onClick) {
        NinePatch normal = new NinePatch(btnTex, 4, 4, 4, 4);
        NinePatch pressed = new NinePatch(btnPressedTex, 4, 4, 4, 4);

        com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle style = new com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle();
        style.up = new NinePatchDrawable(normal);
        style.down = new NinePatchDrawable(pressed);

        com.badlogic.gdx.scenes.scene2d.ui.Button btn = new com.badlogic.gdx.scenes.scene2d.ui.Button(style);

        Label label = new Label(text, new Label.LabelStyle(font, Color.WHITE));
        btn.add(label).pad(6, 10, 6, 10);

        btn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent e, float x, float y, int ptr, int b) {
                return true;
            }

            @Override
            public void touchUp(InputEvent e, float x, float y, int ptr, int b) {
                onClick.run();
            }
        });
        return btn;
    }

    private void addMessage(String text) {
        messages.add(text);
        if (messages.size > 20)
            messages.removeIndex(0);

        chatLog.clear();
        for (String msg : messages) {
            Label lbl = new Label(msg, new Label.LabelStyle(font, Color.WHITE));
            lbl.setWrap(true);
            chatLog.add(lbl).fillX().expandX().padBottom(2).row();
        }
        scrollPane.layout();
        scrollPane.scrollTo(0, 0, 0, 0);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stateTime += delta;

        boolean isMoving = false;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += speed * delta;
            isMoving = true;
            facingRight = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= speed * delta;
            isMoving = true;
            facingRight = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            y += speed * delta;
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y -= speed * delta;
            isMoving = true;
        }

        x = MathUtils.clamp(x, 0, mapW - CAT_W);
        y = MathUtils.clamp(y, 0, mapH - CAT_H);

        float camX = MathUtils.clamp(x + CAT_W / 2f, VIEW_W / 2f, mapW - VIEW_W / 2f);
        float camY = MathUtils.clamp(y + CAT_H / 2f, VIEW_H / 2f, mapH - VIEW_H / 2f);
        camera.position.set(camX, camY, 0);
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        TextureRegion currentFrame = isMoving
                ? runAnimation.getKeyFrame(stateTime, true)
                : idleAnimation.getKeyFrame(stateTime, true);

        if (!facingRight && currentFrame.isFlipX())
            currentFrame.flip(true, false);
        if (facingRight && !currentFrame.isFlipX())
            currentFrame.flip(true, false);

        batch.begin();
        batch.draw(bg, 0, 0, mapW, mapH);
        batch.draw(currentFrame, x, y, CAT_W, CAT_H);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, VIEW_W, VIEW_H);
        stage.getViewport().update(width, height, true);
        stage.clear();
        buildChatUI();
        for (String msg : messages) {
            Label lbl = new Label(msg, new Label.LabelStyle(font, Color.WHITE));
            lbl.setWrap(true);
            chatLog.add(lbl).fillX().expandX().padBottom(2).row();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        bg.dispose();
        idleSheet.dispose();
        runSheet.dispose();
        panelTex.dispose();
        btnTex.dispose();
        btnPressedTex.dispose();
        iconHeart.dispose();
        iconCoin.dispose();
        iconPause.dispose();
        stage.dispose();
        font.dispose();
    }

    public void pause() {
    }

    public void resume() {
    }

    public void hide() {
    }
}