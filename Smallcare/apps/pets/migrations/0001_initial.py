# Generated by Django 3.1.3 on 2020-11-12 20:46

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Animal',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=30)),
                ('photo', models.CharField(max_length=50)),
                ('description', models.CharField(max_length=400)),
            ],
        ),
        migrations.CreateModel(
            name='User',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('nickname', models.CharField(max_length=20)),
                ('password', models.CharField(max_length=20)),
                ('description', models.TextField()),
                ('photo', models.CharField(max_length=50)),
                ('mail', models.CharField(max_length=20)),
                ('phone_number', models.CharField(max_length=15)),
            ],
        ),
        migrations.CreateModel(
            name='Event',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('start_time', models.TimeField()),
                ('end_time', models.TimeField()),
                ('price', models.IntegerField()),
                ('description', models.TextField()),
                ('status', models.CharField(choices=[('r', 'request'), ('p', 'pending'), ('d', 'done')], max_length=1)),
                ('animal_id', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='pets.animal')),
            ],
        ),
        migrations.AddField(
            model_name='animal',
            name='user_id',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='pets.user'),
        ),
    ]
