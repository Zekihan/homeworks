﻿using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Testgram.Api.ApiModels;
using Testgram.Core.Exceptions;
using Testgram.Core.IServices;
using Testgram.Core.Models;

namespace Testgram.Api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class LikeController : ControllerBase
    {
        private readonly ILikeService _likeService;
        private readonly IProfileService _profileService;
        private readonly AutoMapper.IMapper _mapper;

        public LikeController(ILikeService likeService, AutoMapper.IMapper mapper, IProfileService profileService)
        {
            this._mapper = mapper;
            this._likeService = likeService;
            this._profileService = profileService;
        }

        [HttpGet("")]
        public async Task<ActionResult<IEnumerable<LikesModel>>> GetAllLikes()
        {
            var likes = await _likeService.GetAllLikes();
            var likesModel = _mapper.Map<IEnumerable<Likes>, IEnumerable<LikesModel>>(likes);
            foreach (LikesModel like in likesModel)
            {
                var profile = await _profileService.GetProfileById(like.UserId);
                like.Username = profile.Username;
            }
            return Ok(likesModel);
        }

        [HttpGet("username")]
        public async Task<ActionResult<IEnumerable<LikesModel>>> GetAllLikeByUsername(string username)
        {
            var profile = await _profileService.GetProfileByUsername(username);
            if (profile == null)
            {
                return NotFound();
            }
            var likes = await _likeService.GetLikesByUserId(profile.UserId);
            var likesModel = _mapper.Map<IEnumerable<Likes>, IEnumerable<LikesModel>>(likes);
            foreach (LikesModel like in likesModel)
            {
                profile = await _profileService.GetProfileById(like.UserId);
                like.Username = profile.Username;
            }
            return Ok(likesModel);
        }

        [HttpGet("postId")]
        public async Task<ActionResult<IEnumerable<LikesModel>>> GetAllLikeByPostId(long postId)
        {
            var likes = await _likeService.GetLikesByPostId(postId);
            var likesModel = _mapper.Map<IEnumerable<Likes>, IEnumerable<LikesModel>>(likes);
            foreach (LikesModel like in likesModel)
            {
                var profile = await _profileService.GetProfileById(like.UserId);
                like.Username = profile.Username;
            }
            return Ok(likesModel);
        }

        [HttpPost]
        public async Task<ActionResult<LikesModel>> CreateLike(LikesModel newLike)
        {
            try
            {
                var like = _mapper.Map<LikesModel, Likes>(newLike);
                var likeModel = await _likeService.CreateLike(like);

                newLike = _mapper.Map<Likes, LikesModel>(likeModel);
                return Ok(newLike);
            }
            catch (DBException e)
            {
                return BadRequest(e.Message);
            }
            catch (Exception e)
            {
                return BadRequest("Internal error.");
            }
        }

        [HttpDelete("{postId}/{userId}")]
        public async Task<ActionResult<LikesModel>> DeleteLike(long postId, long userId)
        {
            try
            {
                var likeToBeDeleted = await _likeService.GetLikeById(postId, userId);

                if (likeToBeDeleted == null)
                    return NotFound();

                var postModel = _mapper.Map<Likes, LikesModel>(likeToBeDeleted);

                await _likeService.DeleteLike(likeToBeDeleted);
                return Ok(postModel);
            }
            catch (DBException e)
            {
                return BadRequest(e.Message);
            }
            catch (Exception e)
            {
                return BadRequest("Internal error.");
            }
        }
    }
}