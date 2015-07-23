/* == This file is part of Tomahawk Player - <http://tomahawk-player.org> ===
 *
 *   Copyright 2015, Enno Gottschalk <mrmaffen@googlemail.com>
 *
 *   Tomahawk is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   Tomahawk is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with Tomahawk. If not, see <http://www.gnu.org/licenses/>.
 */
package org.tomahawk.libtomahawk.collection;

import org.jdeferred.Promise;
import org.tomahawk.libtomahawk.resolver.Query;

import android.widget.ImageView;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Collection {

    public static final int SORT_NOT = -1;

    public static final int SORT_ALPHA = 0;

    public static final int SORT_ARTIST_ALPHA = 1;

    public static final int SORT_LAST_MODIFIED = 2;

    private final String mId;

    private final String mName;

    protected Set<Album> mAlbums
            = Collections.newSetFromMap(new ConcurrentHashMap<Album, Boolean>());

    protected Set<Artist> mArtists
            = Collections.newSetFromMap(new ConcurrentHashMap<Artist, Boolean>());

    protected Set<Artist> mAlbumArtists
            = Collections.newSetFromMap(new ConcurrentHashMap<Artist, Boolean>());

    protected Set<Query> mQueries
            = Collections.newSetFromMap(new ConcurrentHashMap<Query, Boolean>());

    protected ConcurrentHashMap<Album, List<Query>> mAlbumTracks
            = new ConcurrentHashMap<>();

    protected ConcurrentHashMap<Artist, List<Album>> mArtistAlbums
            = new ConcurrentHashMap<>();

    public Collection(String id, String name) {
        mId = id;
        mName = name;
    }

    public void wipe() {
        mQueries.clear();
        mArtists.clear();
        mAlbums.clear();
        mAlbumTracks.clear();
        mArtistAlbums.clear();
    }

    /**
     * Load this {@link NativeCollection}'s icon into the given {@link ImageView}
     */
    public abstract void loadIcon(ImageView imageView, boolean grayOut);

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public Promise<CollectionCursor<Query>, Throwable, Void> getQueries() {
        return getQueries(SORT_NOT);
    }

    public abstract Promise<CollectionCursor<Query>, Throwable, Void> getQueries(int sortMode);

    public Promise<CollectionCursor<Artist>, Throwable, Void> getArtists() {
        return getArtists(SORT_NOT);
    }

    public abstract Promise<CollectionCursor<Artist>, Throwable, Void> getArtists(int sortMode);

    public Promise<CollectionCursor<Artist>, Throwable, Void> getAlbumArtists() {
        return getAlbumArtists(SORT_NOT);
    }

    public abstract Promise<CollectionCursor<Artist>, Throwable, Void> getAlbumArtists(
            int sortMode);

    public Promise<CollectionCursor<Album>, Throwable, Void> getAlbums() {
        return getAlbums(SORT_NOT);
    }

    public abstract Promise<CollectionCursor<Album>, Throwable, Void> getAlbums(int sortMode);

    public abstract Promise<CollectionCursor<Album>, Throwable, Void> getArtistAlbums(
            Artist artist);

    public abstract Promise<CollectionCursor<Query>, Throwable, Void> getAlbumTracks(Album album);

}
