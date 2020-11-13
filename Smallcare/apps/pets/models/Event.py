from django.db import models
from .Animal import Animal

EventEnum = (('r', 'request'), ('p', 'pending'), ('d', 'done'))

class Event(models.Model):
    animal_id = models.ForeignKey(Animal, on_delete=models.CASCADE)
    start_time = models.TimeField('Час початку')
    end_time = models.TimeField('Час закінчення')
    price = models.IntegerField('Ціна')
    description = models.TextField('Опис')
    status = models.CharField(max_length=1, choices=EventEnum)
