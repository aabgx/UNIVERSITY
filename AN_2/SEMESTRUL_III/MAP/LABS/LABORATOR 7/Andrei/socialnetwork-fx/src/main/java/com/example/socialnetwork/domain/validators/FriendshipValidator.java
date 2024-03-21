package com.example.socialnetwork.domain.validators;

import com.example.socialnetwork.domain.Friendship;

public class FriendshipValidator implements Validator<Friendship> {
    @Override
    public void validate(Friendship entity) throws ValidationException {
        if (entity.getLeft() == null || entity.getRight() == null || entity.getStatus() == null)
            throw new ValidationException("Id1, Id2, Status can't be null!");
        if (entity.getLeft() < 0 || entity.getRight() < 0)
            throw new ValidationException("Id1, Id2 can't be negative!");
        if (entity.getLeft().equals(entity.getRight()))
            throw new ValidationException("Id1 can't be equal to Id2!");
        if (!entity.getStatus().equals("pending") && !entity.getStatus().equals("friends"))
            throw new ValidationException("Status can only be 'pending' or 'friends'!");
    }
}

