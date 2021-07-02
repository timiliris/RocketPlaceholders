/*
 *  This file is part of RocketPlaceholders, licensed under the MIT License.
 *
 *  Copyright (c) Lorenzo0111
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.lorenzo0111.rocketplaceholders.api.impl;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.lorenzo0111.rocketplaceholders.RocketPlaceholders;
import me.lorenzo0111.rocketplaceholders.api.IRocketPlaceholdersAPI;
import me.lorenzo0111.rocketplaceholders.creator.Placeholder;
import me.lorenzo0111.rocketplaceholders.creator.PlaceholdersManager;
import me.lorenzo0111.rocketplaceholders.hooks.PapiHook;
import me.lorenzo0111.rocketplaceholders.providers.Provider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RocketPlaceholdersAPI implements IRocketPlaceholdersAPI {
    private final PlaceholdersManager placeholdersManager;
    private final List<PapiHook> providers = new ArrayList<>();

    public RocketPlaceholdersAPI(PlaceholdersManager placeholdersManager) {
        this.placeholdersManager = placeholdersManager;
    }

    @Override
    public void addPlaceholder(Placeholder placeholder) {
        this.getPlaceholdersManager().getStorageManager().getExternalPlaceholders().add(placeholder.getIdentifier(), placeholder);
    }

    @Override
    public void removePlaceholder(String identifier) {
        this.getPlaceholdersManager().getStorageManager().getExternalPlaceholders().getMap().remove(identifier);
    }

    @Override
    public void removePlaceholder(Placeholder placeholder) {
        this.getPlaceholdersManager().getStorageManager().getExternalPlaceholders().getMap().remove(placeholder.getIdentifier());
    }

    @Override
    public void removePlaceholders(JavaPlugin owner) {
        this.getPlaceholdersManager().getStorageManager().getExternalPlaceholders()
                .getMap()
                .values()
                .stream()
                .filter((placeholder) -> placeholder.getOwner().equals(owner))
                .forEach(this::removePlaceholder);
    }

    @Override
    public void registerProvider(Provider provider, String identifier) {
        PapiHook hook = new PapiHook(provider, identifier);

        providers.add(hook);

        RocketPlaceholders.getInstance()
                .getLogger()
                .info("Loaded custom provider: " + identifier);
    }

    @Override
    public void unloadProviders() {
        providers.forEach(PlaceholderExpansion::unregister);
    }

    @Override
    public @Nullable Placeholder getPlaceholder(String identifier) {
        return this.getPlaceholdersManager().searchPlaceholder(identifier);
    }

    @Override
    public PlaceholdersManager getPlaceholdersManager() {
        return placeholdersManager;
    }
}
